package com.bioautoml.domain.process.service;

import com.bioautoml.domain.file.repository.FileRepository;
import com.bioautoml.domain.file.service.FileService;
import com.bioautoml.domain.outbox.service.OutboxService;
import com.bioautoml.domain.process.dto.ProcessArrangementDTO;
import com.bioautoml.domain.process.dto.ProcessDTO;
import com.bioautoml.domain.process.dto.ProcessMessageDTO;
import com.bioautoml.domain.process.enums.ProcessStatus;
import com.bioautoml.domain.process.enums.ProcessType;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.process.parameters.enums.ParametersType;
import com.bioautoml.domain.process.parameters.form.ParametersForm;
import com.bioautoml.domain.process.parameters.repository.AFEMRepository;
import com.bioautoml.domain.process.parameters.repository.MetalearningRepository;
import com.bioautoml.domain.process.parameters.service.ParametersService;
import com.bioautoml.domain.process.repository.ProcessRepository;
import com.bioautoml.domain.process.types.ProcessSelector;
import com.bioautoml.domain.user.service.UserService;
import com.bioautoml.exceptions.AlreadyExistsException;
import com.bioautoml.exceptions.NotFoundException;
import com.bioautoml.storage.StorageService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProcessService {

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProcessSelector processSelector;

    @Autowired
    private FileService fileService;

    @Autowired
    private StorageService storageService;

    @Autowired
    private ParametersService parametersService;

    @Autowired
    private OutboxService outboxService;

    @Autowired
    private AFEMRepository afemRepository;

    @Autowired
    private MetalearningRepository metalearningRepository;

    @Autowired
    private FileRepository fileRepository;

    @Value("${application.config.processes.amount}")
    private int amountOfRunProcesses;

    private final Gson gson = new Gson();

    private final Logger logger = LoggerFactory.getLogger(ProcessService.class);

    public List<ProcessDTO> getAll(){
        return this.processRepository.findAll()
                .stream()
                .map(ProcessModel::toDTO)
                .collect(Collectors.toList());
    }

    public ProcessDTO getById(UUID id){
        return this.processRepository.findById(id)
                .stream()
                .map(ProcessModel::toDTO)
                .findFirst()
                .orElseThrow(() -> {
                    throw new NotFoundException("Process not Exists!");
                });
    }

    private ProcessDTO save(ProcessModel processModel) {
        return this.processRepository.save(processModel).toDTO();
    }

    @Transactional
    public ProcessDTO start(String processName, Map<String, MultipartFile[]> files, UUID userId, ParametersForm parameters){
        UUID processId = UUID.randomUUID();

        ProcessModel processModel = new ProcessModel();
        processModel.setId(processId);
        processModel.setUserModel(this.userService.getById(userId).toModel());
        processModel.setStartupTime(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        processModel.setProcessType(ProcessType.valueOf(processName));
        logger.info("Created the process: ".concat(processModel.getId().toString()));

        ProcessMessageDTO processMessageDTO = ProcessMessageDTO.builder()
                .id(processId)
                .processStatus(processModel.getProcessStatus())
                .processType(processModel.getProcessType())
                .completionTime(processModel.getCompletionTime())
                .startupTime(processModel.getStartupTime())
                .userId(processModel.getUserModel().getId())
                .build();
        logger.info("Crated the process message: ".concat(processMessageDTO.toString()));

        ProcessDTO processDTO = this.save(processModel);
        this.parametersService.createParameters(processModel.getProcessType(), processModel, parameters);
        this.fileService.save(files, processId);

        this.createFilesInS3(files, processId);

        logger.info("Sent all messages from process ".concat(processId.toString()));

        return processDTO;
    }

    private void createFilesInS3(Map<String, MultipartFile[]> files, UUID processId) {
        List<MultipartFile> allFiles = new ArrayList<>();

        files.forEach((key, value) -> {
            allFiles.addAll(Arrays.asList(value));
        });

        this.storageService.createFolders(allFiles, processId);
    }

    public void updateStatus(UUID id) {
         Optional<ProcessModel> processModelOptional = this.processRepository.findById(id);

         if(processModelOptional.isEmpty()){
             logger.error("Process ".concat(id.toString()).concat(" not exists!"));
             throw new NotFoundException("Process not exists!");
         }

         ProcessModel processModel = processModelOptional.get();
         ProcessStatus nextProcessStatus = processModel.getProcessStatus().next();

         if(nextProcessStatus.equals(ProcessStatus.WAITING)){
             logger.error("Process ".concat(id.toString()).concat(" already completed!"));
             throw new AlreadyExistsException("Process already completed");
         }

         processModel.setProcessStatus(nextProcessStatus);

         logger.info("Process status ".concat(processModel.getId().toString()).concat(" updated from ")
                 .concat(processModel.getProcessStatus().prev().toString()).concat(" to ")
                 .concat(processModel.getProcessStatus().toString()));

         this.save(processModel);
    }

    public void check() {
        long amountOfProcessingProcesses = this.processRepository.countByProcessStatusIs(ProcessStatus.PROCESSING);

        this.processRepository.findByProcessStatusIsOrderByStartupTime(ProcessStatus.WAITING)
                .ifPresent(processes -> {
                    if(amountOfProcessingProcesses < this.amountOfRunProcesses) {
                        processes.stream()
                                .limit(this.amountOfRunProcesses - amountOfProcessingProcesses)
                                .forEach(this::prepareToSend);
                    }
                });
    }

    private void prepareToSend(ProcessModel processModel) {
        ProcessArrangementDTO processArrangementDTO = ProcessArrangementDTO.builder()
                .processModel(processModel)
                .build();

        if(processModel.getProcessType().getParameterType() == ParametersType.AFEM) {
            processArrangementDTO.setParametersEntity(this.afemRepository.findByProcessId(processModel.getId()).get());
        }

        if(processModel.getProcessType().getParameterType() == ParametersType.METALEARNING) {
            processArrangementDTO.setParametersEntity(this.metalearningRepository.findByProcessId(processModel.getId()).get());
        }

        processArrangementDTO.setFiles(this.fileRepository.findAllByProcessModel(processModel).get());
        this.sendToInit(processArrangementDTO);
    }

    private void sendToInit(ProcessArrangementDTO processArrangementDTO) {
        String message = this.gson.toJson(processArrangementDTO);
        logger.info("process to init will to save={}", message);

        Base64.Encoder encoder = Base64.getEncoder();

        this.outboxService.create(encoder.encodeToString(message.getBytes(StandardCharsets.UTF_8)));
    }
}

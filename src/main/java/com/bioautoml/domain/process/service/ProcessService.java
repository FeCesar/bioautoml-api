package com.bioautoml.domain.process.service;

import com.bioautoml.domain.file.model.FileModel;
import com.bioautoml.domain.file.repository.FileRepository;
import com.bioautoml.domain.file.service.FileService;
import com.bioautoml.domain.outbox.service.OutboxService;
import com.bioautoml.domain.process.dto.ProcessArrangementDTO;
import com.bioautoml.domain.process.dto.ProcessDTO;
import com.bioautoml.domain.process.enums.ProcessStatus;
import com.bioautoml.domain.process.enums.ProcessType;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.process.parameters.enums.ParametersType;
import com.bioautoml.domain.process.parameters.form.LabelForm;
import com.bioautoml.domain.process.parameters.form.ParametersForm;
import com.bioautoml.domain.process.parameters.model.LabelModel;
import com.bioautoml.domain.process.parameters.repository.AFEMRepository;
import com.bioautoml.domain.process.parameters.repository.MetalearningRepository;
import com.bioautoml.domain.process.parameters.service.LabelService;
import com.bioautoml.domain.process.parameters.service.ParametersService;
import com.bioautoml.domain.process.repository.ProcessRepository;
import com.bioautoml.domain.user.service.UserService;
import com.bioautoml.exceptions.NotFoundException;
import com.bioautoml.storage.Storage;
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
    private FileService fileService;

    @Autowired
    private Storage storageService;

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

    @Autowired
    private LabelService labelService;

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
                .orElseThrow(() -> new NotFoundException("Process not Exists!"));
    }

    private ProcessDTO save(ProcessModel processModel) {
        return this.processRepository.save(processModel).toDTO();
    }

    @Transactional
    public ProcessDTO start(
            String processName,
            Map<String, MultipartFile[]> files,
            UUID userId,
            ParametersForm parameters,
            LabelForm labelForm
    ){
        UUID processId = UUID.randomUUID();

        ProcessModel processModel = new ProcessModel();
        processModel.setId(processId);
        processModel.setUserModel(this.userService.getById(userId).toModel());
        processModel.setStartupTime(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());

        processModel.setProcessType(ProcessType.valueOf(processName));

        ProcessDTO processDTO = this.save(processModel);
        logger.info("saved process={}", processDTO.getId());

        this.labelService.save(labelForm, processId);
        this.parametersService.createParameters(processModel.getProcessType(), processModel, parameters);
        this.fileService.save(files, processId);

        this.createFilesInS3(files, processId);

        return processDTO;
    }

    private void createFilesInS3(Map<String, MultipartFile[]> files, UUID processId) {
        List<MultipartFile> allFiles = new ArrayList<>();

        files.forEach((key, value) -> {
            allFiles.addAll(Arrays.asList(value));
        });

        this.storageService.createFolders(allFiles, processId);
    }

    public void check() {
        long amountOfProcessingProcesses = this.processRepository.countByProcessStatusIs(ProcessStatus.PROCESSING);
        long amountOfProcessingInQueue = this.processRepository.countByProcessStatusIs(ProcessStatus.IN_QUEUE);
        long totalProcessRunning = amountOfProcessingProcesses + amountOfProcessingInQueue;

        this.processRepository.findByProcessStatusIsOrderByStartupTime(ProcessStatus.WAITING)
            .ifPresent(processes -> {
                if(totalProcessRunning < this.amountOfRunProcesses) {
                    processes.stream()
                        .limit(this.amountOfRunProcesses - totalProcessRunning)
                        .forEach(this::prepareToSend);
                }
            });
    }

    private void prepareToSend(ProcessModel processModel) {
        ProcessArrangementDTO processArrangementDTO = ProcessArrangementDTO.builder()
            .build();

        if(processModel.getProcessType().getParameterType() == ParametersType.AFEM) {
            processArrangementDTO.setParametersEntity(this.afemRepository.findByProcessId(processModel.getId()).get().toVO());
        }

        if(processModel.getProcessType().getParameterType() == ParametersType.METALEARNING) {
            processArrangementDTO.setParametersEntity(this.metalearningRepository.findByProcessId(processModel.getId()).get().toVO());
        }

        processArrangementDTO.setFiles(this.fileRepository.findAllByProcessModel(processModel).get().stream()
                .map(FileModel::toDTO)
                .collect(Collectors.toList()));

        processArrangementDTO.setLabels(this.labelService.findAllByProcess(processModel).get().stream()
                .map(LabelModel::toDTO)
                .collect(Collectors.toList()));

        this.updateStatus(processModel.getId());

        processArrangementDTO.setProcessModel(processModel.toProcessByUserDTO());

        this.sendToInit(processArrangementDTO);
    }

    private void updateStatus(UUID processId) {
        Optional<ProcessModel> processModel = this.processRepository.findById(processId);

        processModel.ifPresent(process -> {
            process.setProcessStatus(ProcessStatus.IN_QUEUE);
            this.processRepository.save(process);
        });
    }

    private void sendToInit(ProcessArrangementDTO processArrangementDTO) {
        String message = this.gson.toJson(processArrangementDTO);

        Base64.Encoder encoder = Base64.getEncoder();
        String encodedMessage = encoder.encodeToString(message.getBytes(StandardCharsets.UTF_8));

        this.outboxService.create(encodedMessage);
    }
}

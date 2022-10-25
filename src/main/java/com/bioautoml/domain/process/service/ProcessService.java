package com.bioautoml.domain.process.service;

import com.bioautoml.domain.message.MessageSender;
import com.bioautoml.domain.process.dto.ProcessDTO;
import com.bioautoml.domain.process.dto.ProcessMessageDTO;
import com.bioautoml.domain.process.dto.ResultObjectCreationRequestTemplateDTO;
import com.bioautoml.domain.process.enums.ProcessStatus;
import com.bioautoml.domain.process.enums.ProcessType;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.process.parameters.service.ParametersService;
import com.bioautoml.domain.process.repository.ProcessRepository;
import com.bioautoml.domain.process.types.ProcessStrategy;
import com.bioautoml.domain.process.types.ProcessSelector;
import com.bioautoml.domain.user.service.UserService;
import com.bioautoml.exceptions.AlreadyExistsException;
import com.bioautoml.exceptions.NotFoundException;
import com.bioautoml.folders.FolderService;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

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
    private MessageSender messageSender;

    @Autowired
    private ProcessSelector processSelector;

    @Value("${application.rabbit.queues.results.generate}")
    private String resultQueueName;

    @Autowired
    private FolderService folderService;

    @Autowired
    private ParametersService parametersService;

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

    public ProcessDTO start(String processName, List<MultipartFile> files, UUID userId){
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

//        List<String> filePaths = this.folderService.createFolders(files, processId);

        this.requestTheStartOfProcess(processMessageDTO, ProcessType.valueOf(processName).getQueueName());
        this.requestCreationOfResultObject(processName, processId, userId);

        logger.info("Sent all messages from process ".concat(processId.toString()));

        ProcessDTO processDTO = this.save(processModel);
        this.parametersService.createParameters(processModel.getProcessType(), processModel, files);

        return processDTO;
    }

    private void requestTheStartOfProcess(ProcessMessageDTO processMessageDTO, String processName){
        String message = this.gson.toJson(processMessageDTO);

        this.messageSender.send(message, processName);
        logger.info("Sent message: ".concat(message));
    }

    private void requestCreationOfResultObject(String processName, UUID processId, UUID userId){
        Optional<ProcessStrategy> process = this.processSelector.getProcessByName(processName);

        if(process.isEmpty()){
            logger.error("Process ".concat(processId.toString()).concat(" not exists!"));
            throw new NotFoundException("Process not exists");
        }

        ResultObjectCreationRequestTemplateDTO templateDTO = ResultObjectCreationRequestTemplateDTO.builder()
                .processId(processId)
                .userId(userId)
                .resultsFields(process.get().getResultsFields())
                .build();

        String message = this.gson.toJson(templateDTO);

        this.messageSender.send(message, this.resultQueueName);
        logger.info("Sent message: ".concat(message));
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

}

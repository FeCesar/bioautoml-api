package com.bioautoml.domain.process.service;

import com.bioautoml.domain.message.model.MessageModel;
import com.bioautoml.domain.message.service.MessageSender;
import com.bioautoml.domain.process.dto.ProcessDTO;
import com.bioautoml.domain.process.dto.ResultObjectCreationRequestTemplateDTO;
import com.bioautoml.domain.process.enums.ProcessStatus;
import com.bioautoml.domain.process.enums.ProcessType;
import com.bioautoml.domain.process.model.ProcessModel;
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

//        this.folderService.createFolders(files, processId);
        this.requestTheStartOfProcess(processModel, ProcessType.valueOf(processName).getQueueName());
        this.requestCreationOfResultObject(processName, processId, userId);

        logger.info("Process ".concat(processId.toString()).concat(" started."));
        return this.save(processModel);
    }

    private void requestTheStartOfProcess(ProcessModel processModel, String processName){
        String message = this.gson.toJson(MessageModel.builder()
                .id(UUID.randomUUID())
                .timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .message(processModel)
                .build());

        logger.info("Sending start process: ".concat(message));
        this.messageSender.send(message, processName);
        logger.info("Sent: ".concat(message));
    }

    private void requestCreationOfResultObject(String processName, UUID processId, UUID userId){
        Optional<ProcessStrategy> process = this.processSelector.getProcessByName(processName);

        if(process.isEmpty()){
            throw new NotFoundException("Process not exists");
        }

        ResultObjectCreationRequestTemplateDTO templateDTO = ResultObjectCreationRequestTemplateDTO.builder()
                .processId(processId)
                .userId(userId)
                .resultsFields(process.get().getResultsFields())
                .build();

        String message = this.gson.toJson(MessageModel.builder()
                .id(UUID.randomUUID())
                .timestamp(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant().toEpochMilli())
                .message(templateDTO)
                .build());

        logger.info("Sending request result object create: ".concat(message));
        this.messageSender.send(message, this.resultQueueName);
        logger.info("Sent: ".concat(message));
    }

    public void updateStatus(UUID id) {
         Optional<ProcessModel> processModelOptional = this.processRepository.findById(id);

         if(processModelOptional.isEmpty()){
             throw new NotFoundException("Process not exists!");
         }

         ProcessModel processModel = processModelOptional.get();
         ProcessStatus nextProcessStatus = processModel.getProcessStatus().next();

         if(nextProcessStatus.equals(ProcessStatus.WAITING)){
             throw new AlreadyExistsException("Process already completed");
         }

         processModel.setProcessStatus(nextProcessStatus);

         logger.info("Updated status from ".concat(processModel.getProcessStatus().prev().toString()).concat(" to ")
            .concat(processModel.getProcessStatus().toString()));

         this.save(processModel);
    }

}

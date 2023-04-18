package com.bioautoml.domain.process.service;

import com.bioautoml.domain.process.dto.ProcessUpdateStatusDTO;
import com.bioautoml.domain.process.enums.ProcessStatus;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.process.repository.ProcessRepository;
import com.bioautoml.exceptions.NotFoundException;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Base64;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProcessUpdateService {

    @Autowired
    private ProcessRepository processRepository;

    private final Gson gson = new Gson();
    private final Logger logger = LoggerFactory.getLogger(ProcessUpdateService.class);

    public void updateStatus(ProcessUpdateStatusDTO processUpdateStatusDTO) {
        UUID processId =  UUID.fromString(processUpdateStatusDTO.getProcessId());
        Optional<ProcessModel> processModelOptional = this.processRepository.findById(processId);

        if(processModelOptional.isEmpty()){
            logger.error("Process={} not exists!", processId);
            throw new NotFoundException("Process not exists!");
        }

        ProcessModel processModel = processModelOptional.get();
        ProcessStatus processStatus = ProcessStatus.valueOf(processUpdateStatusDTO.getStatus());
        processModel.setProcessStatus(processStatus);

        if (processStatus == ProcessStatus.FINISHED || processStatus == ProcessStatus.ERROR) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            LocalDateTime referenceDate = LocalDateTime.parse(processUpdateStatusDTO.getReferenceDate(), formatter);
            processModel.setCompletionTime(referenceDate.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli());
        }

        this.processRepository.save(processModel);
    }

    public void decode(String encodedUpdateProcessStatus) {
        try{
            String updateProcessJson = new String(Base64.getDecoder().decode(encodedUpdateProcessStatus));
            ProcessUpdateStatusDTO updateStatusDTO = this.gson.fromJson(updateProcessJson, ProcessUpdateStatusDTO.class);

            this.updateStatus(updateStatusDTO);
        } catch (Exception e) {
            logger.error("error from job can not deseralizable={}", encodedUpdateProcessStatus);
        }
    }

}

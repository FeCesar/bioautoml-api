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
            logger.error("Process ".concat(processId.toString()).concat(" not exists!"));
            throw new NotFoundException("Process not exists!");
        }

        ProcessModel processModel = processModelOptional.get();
        processModel.setProcessStatus(
            ProcessStatus.valueOf(processUpdateStatusDTO.getStatus())
        );

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

package com.bioautoml.domain.process.service;

import com.bioautoml.domain.message.model.MessageModel;
import com.bioautoml.domain.message.service.MessageSender;
import com.bioautoml.domain.process.dto.ProcessDTO;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.process.repository.ProcessRepository;
import com.bioautoml.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProcessService {

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private MessageSender messageSender;

    @Value("${application.rabbit.queues.process}")
    private String processQueue;

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

    public ProcessDTO start(ProcessModel processModel){



        this.messageSender.send(MessageModel.builder()
                .id(UUID.randomUUID())
                .date(LocalDateTime.now())
                .message(processModel.toDTO())
                .build(), this.processQueue);

        return this.save(processModel);
    }
}

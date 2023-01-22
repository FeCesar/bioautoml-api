package com.bioautoml.domain.file.service;

import com.bioautoml.domain.file.dto.FileDTO;
import com.bioautoml.domain.file.enums.FileType;
import com.bioautoml.domain.file.model.FileModel;
import com.bioautoml.domain.file.repository.FileRepository;
import com.bioautoml.domain.message.MessageSender;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.process.repository.ProcessRepository;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private MessageSender messageSender;

    @Value("${application.rabbit.queues.process.files}")
    private String processFileQueue;

    private final Gson gson = new Gson();

    @Transactional
    public void save(Map<String, MultipartFile[]> files, UUID processId) {
        this.processRepository.findById(processId).stream()
                .findFirst()
                .ifPresent(processModel -> files.forEach((key, value) -> {
                    List<MultipartFile> allFilesByType = Arrays.asList(value);

                    allFilesByType.forEach(multipartFile -> {
                        FileModel fileModel = new FileModel();
                        fileModel.setId(UUID.randomUUID());
                        fileModel.setFileName(multipartFile.getOriginalFilename());
                        fileModel.setFileType(FileType.valueOf(key));
                        fileModel.setProcessModel(processModel);

                        this.fileRepository.save(fileModel);
                    });

                    this.sendMessageFiles(processModel);
                }));
    }

    private void sendMessageFiles(ProcessModel processModel) {
        this.fileRepository.findAllByProcessModel(processModel)
                .ifPresent(fileModels -> {
                    fileModels.stream()
                        .map(FileModel::toDTO)
                        .collect(Collectors.toList())
                            .forEach(fileDTO -> {
                                String message = this.gson.toJson(fileDTO);
                                this.messageSender.send(message, this.processFileQueue);
                            });
                });
    }

}

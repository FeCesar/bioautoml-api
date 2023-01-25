package com.bioautoml.domain.file.service;

import com.bioautoml.domain.file.enums.FileType;
import com.bioautoml.domain.file.model.FileModel;
import com.bioautoml.domain.file.repository.FileRepository;
import com.bioautoml.domain.process.repository.ProcessRepository;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

@Service
public class FileService {

    @Autowired
    private FileRepository fileRepository;

    @Autowired
    private ProcessRepository processRepository;

    private final Gson gson = new Gson();

    private static final Logger logger = LoggerFactory.getLogger(FileService.class);

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
                        logger.info("file={} saved", fileModel.getId());
                    });
                }));
    }

}

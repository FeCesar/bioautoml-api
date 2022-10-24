package com.bioautoml.domain.process.parameters.service;

import com.bioautoml.domain.process.enums.ProcessType;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.process.parameters.dto.AFEMDTO;
import com.bioautoml.domain.process.parameters.dto.MetalearningDTO;
import com.bioautoml.domain.process.parameters.service.strategy.AFEMService;
import com.bioautoml.domain.process.parameters.service.strategy.MetalearningService;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@Service
public class ParametersService {

    @Autowired
    private AFEMService afemService;

    @Autowired
    private MetalearningService metalearningService;

    private final Gson gson = new Gson();

    public void createParameters(ProcessType processType, ProcessModel processModel, List<MultipartFile> files) {

        switch (processType.getParameterType()){
            case AFEM:
                AFEMDTO afemdto = gson.fromJson(this.getJsonFile(files).toString(), AFEMDTO.class);
                afemdto.setId(UUID.randomUUID());
                afemdto.setProcess(processModel.toDTO());

                afemService.save(afemdto.toModel());
                break;

            case METALEARNING:
                MetalearningDTO metalearningDTO = gson.fromJson(this.getJsonFile(files).toString(), MetalearningDTO.class);
                metalearningDTO.setId(UUID.randomUUID());
                metalearningDTO.setProcess(processModel.toDTO());

                metalearningService.save(metalearningDTO.toModel());
                break;
        }
    }

    private Object getJsonFile(List<MultipartFile> files){
        return files.stream()
                .filter(file -> Objects.requireNonNull(file.getOriginalFilename()).contains(".json"))
                .limit(1)
                .map((Function<MultipartFile, Object>) this::getFileString)
                .findFirst()
                .orElse(null);
    }

    private String getFileString(MultipartFile file){
        try{
            return IOUtils.toString(file.getInputStream(), StandardCharsets.UTF_8);
        } catch (Exception e){
            throw new IllegalStateException("");
        }
    }
}

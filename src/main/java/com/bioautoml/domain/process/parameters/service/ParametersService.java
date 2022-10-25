package com.bioautoml.domain.process.parameters.service;

import com.bioautoml.domain.process.enums.ProcessType;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.process.parameters.dto.AFEMDTO;
import com.bioautoml.domain.process.parameters.dto.MetalearningDTO;
import com.bioautoml.domain.process.parameters.service.strategy.AFEMService;
import com.bioautoml.domain.process.parameters.service.strategy.MetalearningService;
import com.google.gson.Gson;
import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
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

    @Value("${application.output.init.path}")
    private String initOutputPath;

    @Value("${application.output.end.path}")
    private String endOutputPath;

    private final Gson gson = new Gson();

    private static final String SEPARATOR = "/";

    private final Logger logger = LoggerFactory.getLogger(ParametersService.class);

    public void createParameters(ProcessType processType, ProcessModel processModel, List<MultipartFile> files) {

        switch (processType.getParameterType()){
            case AFEM:
                AFEMDTO afemdto = gson.fromJson(this.getJsonFile(files).toString(), AFEMDTO.class);
                afemdto.setId(UUID.randomUUID());
                afemdto.setProcess(processModel.toDTO());
                afemdto.setOutput(this.createOutputPath(processModel.getId()));
                logger.info("Created the AFEM parameters from ".concat(afemdto.getId().toString()).concat(" process!"));

                afemService.save(afemdto.toModel());
                break;

            case METALEARNING:
                MetalearningDTO metalearningDTO = gson.fromJson(this.getJsonFile(files).toString(), MetalearningDTO.class);
                metalearningDTO.setId(UUID.randomUUID());
                metalearningDTO.setProcess(processModel.toDTO());
                metalearningDTO.setOutput(this.createOutputPath(processModel.getId()));
                logger.info("Created the Metaleraning parameters from ".concat(metalearningDTO.getId().toString()).concat(" process!"));

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
        } catch (IOException e){
            logger.error("When get string from json file.");
            throw new IllegalStateException("It sent invalid json file.");
        }
    }

    private String createOutputPath(UUID processId){
        return this.initOutputPath.concat(SEPARATOR).concat(processId.toString()).concat(this.endOutputPath).concat(SEPARATOR);
    }
}

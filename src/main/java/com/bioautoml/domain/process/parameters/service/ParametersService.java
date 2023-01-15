package com.bioautoml.domain.process.parameters.service;

import com.bioautoml.domain.message.MessageSender;
import com.bioautoml.domain.process.enums.ProcessType;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.process.parameters.dto.AFEMDTO;
import com.bioautoml.domain.process.parameters.dto.MetalearningDTO;
import com.bioautoml.domain.process.parameters.model.ParametersEntity;
import com.bioautoml.domain.process.parameters.service.strategy.AFEMServiceStrategy;
import com.bioautoml.domain.process.parameters.service.strategy.MetalearningServiceStrategy;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ParametersService {

    @Autowired
    private AFEMServiceStrategy afemService;

    @Autowired
    private MetalearningServiceStrategy metalearningService;

    @Autowired
    private MessageSender messageSender;

    @Value("${application.output.init.path}")
    private String initOutputPath;

    @Value("${application.output.end.path}")
    private String endOutputPath;

    @Value("${application.rabbit.queues.process.parameters.afem}")
    private String afemParametersQueue;

    @Value("${application.rabbit.queues.process.parameters.metalearning}")
    private String metalearningParametersQueue;

    private final Gson gson = new Gson();

    private static final String SEPARATOR = "/";

    private final Logger logger = LoggerFactory.getLogger(ParametersService.class);

    public void createParameters(ProcessType processType, ProcessModel processModel, ParametersEntity parameters) {

        switch (processType.getParameterType()){
            case AFEM:
                AFEMDTO afemdto = new AFEMDTO();
                BeanUtils.copyProperties(parameters, afemdto);

                afemdto.setId(UUID.randomUUID());
                afemdto.setProcess(processModel.toDTO());
                afemdto.setOutput(this.createOutputPath(processModel.getId()));

                logger.info("Created the AFEM parameters from ".concat(afemdto.getId().toString()).concat(" process!"));

                afemService.save(afemdto.toModel());

                String AFEMMessage = this.gson.toJson(afemdto.toVO());
                this.messageSender.send(AFEMMessage, this.afemParametersQueue);
                break;

            case METALEARNING:
                MetalearningDTO metalearningDTO = new MetalearningDTO();
                BeanUtils.copyProperties(parameters, metalearningDTO);

                metalearningDTO.setId(UUID.randomUUID());
                metalearningDTO.setProcess(processModel.toDTO());
                metalearningDTO.setOutput(this.createOutputPath(processModel.getId()));
                logger.info("Created the Metaleraning parameters from ".concat(metalearningDTO.getId().toString()).concat(" process!"));

                metalearningService.save(metalearningDTO.toModel());

                String metalearningMessage = this.gson.toJson(metalearningDTO.toVO());
                this.messageSender.send(metalearningMessage, this.metalearningParametersQueue);
                break;
        }
    }

    private String createOutputPath(UUID processId){
        return this.initOutputPath.concat(SEPARATOR).concat(processId.toString()).concat(this.endOutputPath).concat(SEPARATOR);
    }
}

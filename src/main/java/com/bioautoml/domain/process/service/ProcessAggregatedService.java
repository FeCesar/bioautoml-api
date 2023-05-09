package com.bioautoml.domain.process.service;

import com.bioautoml.domain.file.dto.FileSimpleDTO;
import com.bioautoml.domain.file.model.FileModel;
import com.bioautoml.domain.file.service.FileService;
import com.bioautoml.domain.process.dto.ProcessAggregatedDTO;
import com.bioautoml.domain.process.dto.ProcessSimpleDTO;
import com.bioautoml.domain.process.model.ProcessModel;
import com.bioautoml.domain.process.parameters.dto.LabelSimpleDTO;
import com.bioautoml.domain.process.parameters.enums.ParametersType;
import com.bioautoml.domain.process.parameters.model.LabelModel;
import com.bioautoml.domain.process.parameters.model.ParametersEntity;
import com.bioautoml.domain.process.parameters.service.LabelService;
import com.bioautoml.domain.process.parameters.service.strategy.AFEMServiceStrategy;
import com.bioautoml.domain.process.parameters.service.strategy.MetalearningServiceStrategy;
import com.bioautoml.domain.process.repository.ProcessRepository;
import com.bioautoml.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ProcessAggregatedService {

    @Autowired
    private MetalearningServiceStrategy metalearningServiceStrategy;

    @Autowired
    private AFEMServiceStrategy afemServiceStrategy;

    @Autowired
    private ProcessRepository processRepository;

    @Autowired
    private LabelService labelService;

    @Autowired
    private FileService fileService;

    public ProcessAggregatedDTO getAllInfoFrom(UUID processId){

        Optional<ProcessModel> processModel =  this.processRepository.findById(processId);

        ProcessSimpleDTO processSimpleDTO = processModel
            .stream()
            .map(ProcessModel::toSimpleDTO)
            .findFirst()
            .orElseThrow(() -> new NotFoundException("Process not Exists!"));

        ParametersEntity parameterSimpleDTO;

        if(processSimpleDTO.getProcessType().getParameterType() == ParametersType.AFEM) {
            parameterSimpleDTO  = afemServiceStrategy.getModelByProcessId(processId).toSimpleDTO();
        } else{
            parameterSimpleDTO = metalearningServiceStrategy.getModelByProcessId(processId).toSimpleDTO();
        }

        List<LabelSimpleDTO> labelSimpleDTOS = this.labelService.findAllByProcess(processModel.get()).get()
                .stream()
                .map(LabelModel::toSimpleDTO)
                .collect(Collectors.toList());

        List<FileSimpleDTO> fileSimpleDTOS = this.fileService.getAllBy(processModel.get())
                .stream()
                .map(FileModel::toSimpleDTO)
                .collect(Collectors.toList());

        return ProcessAggregatedDTO.builder()
                .process(processSimpleDTO)
                .parameters(parameterSimpleDTO)
                .labels(labelSimpleDTOS)
                .files(fileSimpleDTOS)
                .build();
    }


}

package com.bioautoml.domain.process.parameters.service.strategy;

import com.bioautoml.domain.process.parameters.dto.MetalearningDTO;
import com.bioautoml.domain.process.parameters.dto.MetalearningResponseDTO;
import com.bioautoml.domain.process.parameters.dto.MetalearningSimpleDTO;
import com.bioautoml.domain.process.parameters.model.MetalearningModel;
import com.bioautoml.domain.process.parameters.repository.MetalearningRepository;
import com.bioautoml.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MetalearningServiceStrategy implements ParametersServiceStrategy<MetalearningModel, MetalearningResponseDTO> {

    @Autowired
    private MetalearningRepository metalearningRepository;

    @Override
    public void save(MetalearningModel model) {
        this.metalearningRepository.save(model);
    }

    @Override
    public MetalearningResponseDTO getByProcessId(Long id) {
        return this.metalearningRepository.findByProcessId(id)
                .stream()
                .map(MetalearningModel::toResponseDTO)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Process not Exists!"));
    }

    @Override
    public MetalearningModel getModelByProcessId(Long id) {
        return this.metalearningRepository.findByProcessId(id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Process not Exists!"));
    }
}

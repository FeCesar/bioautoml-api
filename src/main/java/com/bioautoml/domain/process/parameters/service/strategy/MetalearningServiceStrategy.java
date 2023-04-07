package com.bioautoml.domain.process.parameters.service.strategy;

import com.bioautoml.domain.process.parameters.dto.MetalearningDTO;
import com.bioautoml.domain.process.parameters.dto.MetalearningResponseDTO;
import com.bioautoml.domain.process.parameters.model.MetalearningModel;
import com.bioautoml.domain.process.parameters.repository.MetalearningRepository;
import com.bioautoml.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MetalearningServiceStrategy implements ParametersServiceStrategy<MetalearningModel, MetalearningDTO, MetalearningResponseDTO> {

    @Autowired
    private MetalearningRepository metalearningRepository;

    @Override
    public MetalearningDTO save(MetalearningModel model) {
        return this.metalearningRepository.save(model).toDTO();
    }

    @Override
    public List<MetalearningDTO> getAll() {
        return this.metalearningRepository.findAll()
                .stream()
                .map(MetalearningModel::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public MetalearningResponseDTO getByProcessId(UUID id) {
        return this.metalearningRepository.findByProcessId(id)
                .stream()
                .map(MetalearningModel::toResponseDTO)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Process not Exists!"));
    }
}

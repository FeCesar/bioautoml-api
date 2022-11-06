package com.bioautoml.domain.process.parameters.service.strategy;

import com.bioautoml.domain.process.parameters.dto.AFEMDTO;
import com.bioautoml.domain.process.parameters.dto.AFEMResponseDTO;
import com.bioautoml.domain.process.parameters.model.AFEMModel;
import com.bioautoml.domain.process.parameters.repository.AFEMRepository;
import com.bioautoml.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class AFEMServiceStrategy implements ParametersServiceStrategy<AFEMModel, AFEMDTO, AFEMResponseDTO> {

    @Autowired
    private AFEMRepository afemRepository;

    @Override
    public AFEMDTO save(AFEMModel model) {
        return this.afemRepository.save(model).toDTO();
    }

    @Override
    public List<AFEMDTO> getAll() {
        return this.afemRepository.findAll()
                .stream()
                .map(AFEMModel::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public AFEMResponseDTO getByProcessId(UUID id) {
        return this.afemRepository.findByProcessId(id)
                .stream()
                .map(AFEMModel::toResponseDTO)
                .findFirst()
                .orElseThrow(() -> {
                    throw new NotFoundException("Process not Exists!");
                });
    }
}

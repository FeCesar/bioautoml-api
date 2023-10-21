package com.bioautoml.domain.process.parameters.service.strategy;

import com.bioautoml.domain.process.parameters.dto.AFEMDTO;
import com.bioautoml.domain.process.parameters.dto.AFEMResponseDTO;
import com.bioautoml.domain.process.parameters.model.AFEMModel;
import com.bioautoml.domain.process.parameters.repository.AFEMRepository;
import com.bioautoml.exceptions.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AFEMServiceStrategy implements ParametersServiceStrategy<AFEMModel, AFEMResponseDTO> {

    @Autowired
    private AFEMRepository afemRepository;

    @Override
    public void save(AFEMModel model) {
        this.afemRepository.save(model);
    }

    @Override
    public AFEMResponseDTO getByProcessId(Long id) {
        return this.afemRepository.findByProcessId(id)
                .stream()
                .map(AFEMModel::toResponseDTO)
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Process not Exists!"));
    }

    @Override
    public AFEMModel getModelByProcessId(Long id) {
        return this.afemRepository.findByProcessId(id)
                .stream()
                .findFirst()
                .orElseThrow(() -> new NotFoundException("Process not Exists!"));
    }
}

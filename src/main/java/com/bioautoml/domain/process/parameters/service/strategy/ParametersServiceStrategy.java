package com.bioautoml.domain.process.parameters.service.strategy;

import java.util.List;
import java.util.UUID;

public interface ParametersServiceStrategy<ParameterModel, ParameterDTO, ParameterResponseDTO> {

    ParameterDTO save(ParameterModel model);
    List<ParameterDTO> getAll();
    ParameterResponseDTO getByProcessId(UUID id);

}

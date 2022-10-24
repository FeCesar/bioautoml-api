package com.bioautoml.domain.process.parameters.service.strategy;

import java.util.List;
import java.util.UUID;

public interface ParametersService<ParameterModel, ParameterDTO, ParameterResponseDTO> {

    ParameterDTO save(ParameterModel model);
    List<ParameterDTO> getAll();
    ParameterResponseDTO getByProcessId(UUID id);

}

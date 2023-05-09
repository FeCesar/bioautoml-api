package com.bioautoml.domain.process.parameters.service.strategy;

import java.util.List;
import java.util.UUID;

public interface ParametersServiceStrategy<ParameterModel, ParameterResponseDTO> {

    void save(ParameterModel model);

    ParameterResponseDTO getByProcessId(UUID id);

    ParameterModel getModelByProcessId(UUID id);

}

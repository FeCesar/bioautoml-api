package com.bioautoml.domain.process.parameters.dto;

import com.bioautoml.domain.process.parameters.model.ParametersEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AFEMSimpleDTO implements Serializable, ParametersEntity {

    public Integer estimations;
    public Integer cpuNumbers;

}

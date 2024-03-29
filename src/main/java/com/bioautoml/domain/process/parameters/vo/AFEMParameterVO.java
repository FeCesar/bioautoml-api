package com.bioautoml.domain.process.parameters.vo;

import com.bioautoml.domain.process.parameters.model.ParametersEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AFEMParameterVO implements Serializable, ParametersEntity {

    public UUID id;
    public Integer estimations = 50;
    public Integer cpuNumbers = 1;
    public String output;
    private Long processId;

}

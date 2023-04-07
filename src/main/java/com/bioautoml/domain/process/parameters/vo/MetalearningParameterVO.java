package com.bioautoml.domain.process.parameters.vo;

import com.bioautoml.domain.process.parameters.model.ParametersEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MetalearningParameterVO implements Serializable, ParametersEntity {

    private UUID id;
    private Boolean normalization = false;
    private Integer cpuNumbers = 1;
    private Integer classifiers;
    private Boolean imbalance = false;
    private Boolean tuning = false;
    public String output;
    private UUID processId;

}

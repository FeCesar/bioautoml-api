package com.bioautoml.domain.process.parameters.dto;

import com.bioautoml.domain.process.parameters.enums.Classifiers;
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
public class MetalearningSimpleDTO implements Serializable, ParametersEntity {

    private Boolean normalization;
    private Integer cpuNumbers;
    private Classifiers classifiers;
    private Boolean imbalance;
    private Boolean tuning;

}

package com.bioautoml.domain.process.parameters.dto;

import com.bioautoml.domain.process.parameters.enums.Classifiers;
import com.bioautoml.domain.process.parameters.model.ParametersEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MetalearningResponseDTO implements ParametersEntity {

    private UUID id;
    private String train;
    private String trainLabel;
    private String test;
    private String testLabel;
    private String testNamesEq;
    private Boolean nf = false;
    private Integer cpuNumbers = 1;
    private Classifiers classifiers;
    private Boolean imbalance = false;
    private Boolean tuning = false;
    public String output;

}

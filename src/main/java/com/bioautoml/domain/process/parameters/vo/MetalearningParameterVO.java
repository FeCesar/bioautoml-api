package com.bioautoml.domain.process.parameters.vo;

import com.bioautoml.domain.process.parameters.enums.Classifiers;
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
public class MetalearningParameterVO implements Serializable {

    private UUID id;
    private String train;
    private String trainLabel;
    private String test;
    private String testLabel;
    private String testNamesEq;
    private Boolean normalization = false;
    private Integer cpuNumbers = 1;
    private Classifiers classifiers;
    private Boolean imbalance = false;
    private Boolean tuning = false;
    public String output;
    private UUID processId;

}

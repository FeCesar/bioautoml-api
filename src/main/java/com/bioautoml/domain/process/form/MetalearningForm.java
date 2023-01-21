package com.bioautoml.domain.process.form;

import com.bioautoml.domain.process.parameters.enums.Classifiers;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class MetalearningForm implements ParametersForm {

    private Boolean normalization;
    private Integer cpuNumbers;
    private Classifiers classifiers;
    private Boolean imbalance;
    private Boolean tuning;

}

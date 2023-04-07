package com.bioautoml.domain.process.parameters.form;

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

    private Boolean normalization = false;
    private Integer cpuNumbers = 1;
    private Classifiers classifiers;
    private Boolean imbalance = false;
    private Boolean tuning = false;

}

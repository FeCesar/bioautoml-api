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

    private String train;
    private String trainLabel;
    private String test;
    private String testLabel;
    private String testNameEq;
    private Classifiers classifiers;

}

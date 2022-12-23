package com.bioautoml.domain.process.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AFEMForm implements ParametersForm {

    private String fastaTrain;
    private String fastaLabelTrain;
    private String fastaTest;
    private String fastaLabelTest;

}

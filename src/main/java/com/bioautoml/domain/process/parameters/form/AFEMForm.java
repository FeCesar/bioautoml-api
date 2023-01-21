package com.bioautoml.domain.process.parameters.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AFEMForm implements ParametersForm {

    private Integer estimations;
    private Integer cpuNumbers;

}

package com.bioautoml.domain.process.enums;

import com.bioautoml.domain.process.parameters.enums.ParametersType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ProcessType {
    DNA_RNA("DNA_RNA", ParametersType.AFEM),
    WITH_NUMERICAL_MAPPING_DNA_RNA("WITH_NUMERICAL_MAPPING_DNA_RNA", ParametersType.AFEM),
    PROTEIN("PROTEIN", ParametersType.AFEM),
    BINARY_PROBLEMS("BINARY_PROBLEMS", ParametersType.METALEARNING),
    MULTICLASS_PROBLEMS("MULTICLASS_PROBLEMS", ParametersType.METALEARNING),

    IFEATURE_PROTEIN("IFEATURE_PROTEIN", ParametersType.AFEM);

    private String processType;
    private ParametersType parameterType;

}

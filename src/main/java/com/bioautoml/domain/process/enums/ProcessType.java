package com.bioautoml.domain.process.enums;

import com.bioautoml.domain.process.parameters.enums.ParametersType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ProcessType {
    DNA_RNA("DNA_RNA", "dna-rna", ParametersType.AFEM),
    WITH_NUMERICAL_MAPPING_DNA_RNA("WITH_NUMERICAL_MAPPING_DNA_RNA", "wnm-dna-rna", ParametersType.AFEM),
    PROTEIN("PROTEIN", "protein", ParametersType.AFEM),
    BINARY_PROBLEMS("BINARY_PROBLEMS", "binary-problems", ParametersType.METALEARNING),
    MULTICLASS_PROBLEMS("MULTICLASS_PROBLEMS", "multiclass-problems", ParametersType.METALEARNING);

    private String processType;
    private String queueName;
    private ParametersType parameterType;

}

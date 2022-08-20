package com.bioautoml.domain.process.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ProcessType {
    DNA_RNA("DNA_RNA", "dna-rna"),
    WITH_NUMERICAL_MAPPING_DNA_RNA("WITH_NUMERICAL_MAPPING_DNA_RNA", "wnm-dna-rna"),
    PROTEIN("PROTEIN", "protein"),
    BINARY_PROBLEMS("BINARY_PROBLEMS", "binary-problems"),
    MULTICLASS_PROBLEMS("MULTICLASS_PROBLEMS", "multiclass-problems");

    private String processType;
    private String queueName;

}

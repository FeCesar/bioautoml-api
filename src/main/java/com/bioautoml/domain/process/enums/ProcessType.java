package com.bioautoml.domain.process.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public enum ProcessType {
    DNA_RNA("DNA_RNA"),
    WITH_NUMERICAL_MAPPING_DNA_RNA("WITH_NUMERICAL_MAPPING_DNA_RNA"),
    PROTEIN("PROTEIN"),
    BINARY_PROBLEMS("BINARY_PROBLEMS"),
    MULTICLASS_PROBLEMS("MULTICLASS_PROBLEMS");

    private String ProcessType;

}

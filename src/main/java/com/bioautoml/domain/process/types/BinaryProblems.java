package com.bioautoml.domain.process.types;

import com.bioautoml.domain.process.enums.ProcessType;

public class BinaryProblems implements ProcessStrategy {

    @Override
    public String getName() {
        return ProcessType.BINARY_PROBLEMS.getProcessType();
    }

    @Override
    public Integer getResultsFields() {
        return 5;
    }

}

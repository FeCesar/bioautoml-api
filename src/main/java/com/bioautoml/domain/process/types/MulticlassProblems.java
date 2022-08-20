package com.bioautoml.domain.process.types;

import com.bioautoml.domain.process.enums.ProcessType;

public class MulticlassProblems implements Process{
    @Override
    public String getName() {
        return ProcessType.MULTICLASS_PROBLEMS.getProcessType();
    }

    @Override
    public Integer getResultsFields() {
        return 4;
    }

}

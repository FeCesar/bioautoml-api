package com.bioautoml.domain.process.types;

import com.bioautoml.domain.process.enums.ProcessType;

public class Protein implements ProcessStrategy {

    @Override
    public String getName() {
        return ProcessType.PROTEIN.getProcessType();
    }

    @Override
    public Integer getResultsFields() {
        return 2;
    }

}

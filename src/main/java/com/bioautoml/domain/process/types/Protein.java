package com.bioautoml.domain.process.types;

import com.bioautoml.domain.process.enums.ProcessType;

public class Protein implements Process{
    @Override
    public String getName() {
        return ProcessType.PROTEIN.getProcessType();
    }

    @Override
    public Integer getResultsFields() {
        return 2;
    }

    public static Process getInstance() {
        return new Protein();
    }
}

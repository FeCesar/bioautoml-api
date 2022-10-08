package com.bioautoml.domain.process.types;

import com.bioautoml.domain.process.enums.ProcessType;

public class DnaRna implements ProcessStrategy {

    @Override
    public String getName() {
        return ProcessType.DNA_RNA.getProcessType();
    }

    @Override
    public Integer getResultsFields() {
        return 6;
    }

}

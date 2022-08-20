package com.bioautoml.domain.process.types;

import com.bioautoml.domain.process.enums.ProcessType;

public class WNMDnaRna implements Process{
    @Override
    public String getName() {
        return ProcessType.WITH_NUMERICAL_MAPPING_DNA_RNA.getProcessType();
    }

    @Override
    public Integer getResultsFields() {
        return 4;
    }

    public static Process getInstance() {
        return new WNMDnaRna();
    }
}

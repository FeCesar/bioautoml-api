package com.bioautoml.domain.process.types;

import java.io.Serializable;

public interface ProcessStrategy extends Serializable {

    String getName();
    Integer getResultsFields();

}

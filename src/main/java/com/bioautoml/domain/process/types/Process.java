package com.bioautoml.domain.process.types;

import java.io.Serializable;

public interface Process extends Serializable {

    String getName();
    Integer getResultsFields();

}

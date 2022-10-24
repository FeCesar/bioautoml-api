package com.bioautoml.domain.process.parameters.enums;

public enum Classifiers {

    CATBOOST(0),
    RANDOM_FOREST(1),
    LIGHT_GBM(2);

    private final Integer index;

    Classifiers(Integer index) {
        this.index = index;
    }

    public Integer getIndex(){
        return this.index;
    }
}

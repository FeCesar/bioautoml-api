package com.bioautoml.domain.process.enums;

public enum ProcessStatus {

    WAITING, PROCESSING, FINISHED;

    static public final ProcessStatus[] values = values();

    public ProcessStatus prev() {
        return values[(ordinal() - 1  + values.length) % values.length];
    }

    public ProcessStatus next() {
        return values[(ordinal() + 1) % values.length];
    }

}

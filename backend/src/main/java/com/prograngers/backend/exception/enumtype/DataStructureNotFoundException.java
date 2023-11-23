package com.prograngers.backend.exception.enumtype;

import static com.prograngers.backend.exception.ErrorCode.DATASTRUCTURE_NOT_EXISTS;

public class DataStructureNotFoundException extends EnumTypeException {

    public DataStructureNotFoundException() {
        super(DATASTRUCTURE_NOT_EXISTS);
    }
}

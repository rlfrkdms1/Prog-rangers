package com.prograngers.backend.exception.enumtype;

import static com.prograngers.backend.exception.errorcode.SolutionErrorCode.DATA_STRUCTURE_NOT_EXISTS;

public class DataStructureNotFoundException extends EnumTypeException {

    public DataStructureNotFoundException() {
        super(DATA_STRUCTURE_NOT_EXISTS);
    }
}

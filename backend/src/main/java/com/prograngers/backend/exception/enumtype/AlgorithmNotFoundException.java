package com.prograngers.backend.exception.enumtype;

import static com.prograngers.backend.exception.errorcode.SolutionErrorCode.ALGORITHM_NOT_EXISTS;

public class AlgorithmNotFoundException extends EnumTypeException {

    public AlgorithmNotFoundException() {
        super(ALGORITHM_NOT_EXISTS);
    }
}

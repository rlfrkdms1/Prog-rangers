package com.prograngers.backend.exception.enumtype;

import static com.prograngers.backend.exception.ErrorCode.ALGORITHM_NOT_EXISTS;

public class AlgorithmNotFoundException extends EnumTypeException {

    public AlgorithmNotFoundException() {
        super("알고리즘 타입을 확인해 주세요", ALGORITHM_NOT_EXISTS);
    }
}

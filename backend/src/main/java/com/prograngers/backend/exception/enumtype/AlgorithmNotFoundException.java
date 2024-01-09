package com.prograngers.backend.exception.enumtype;

import static com.prograngers.backend.exception.ErrorCodeBefore.ALGORITHM_NOT_EXISTS;

public class AlgorithmNotFoundException extends EnumTypeException {

    public AlgorithmNotFoundException() {
        super(ALGORITHM_NOT_EXISTS, "알고리즘 타입을 확인해 주세요");
    }
}

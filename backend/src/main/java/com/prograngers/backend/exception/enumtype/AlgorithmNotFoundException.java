package com.prograngers.backend.exception.enumtype;

import com.prograngers.backend.exception.notfound.NotFoundException;

import static com.prograngers.backend.exception.ErrorCode.ALGORITHM_NOT_EXISTS;
import static com.prograngers.backend.exception.ErrorCode.INVALID_SOLUTION_BODY;

public class AlgorithmNotFoundException extends EnumTypeException {

    public AlgorithmNotFoundException() {
        super(ALGORITHM_NOT_EXISTS, "알고리즘 타입을 확인해 주세요");
    }
}

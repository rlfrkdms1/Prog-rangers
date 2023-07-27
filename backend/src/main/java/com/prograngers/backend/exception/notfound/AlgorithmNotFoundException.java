package com.prograngers.backend.exception.notfound;

import static com.prograngers.backend.exception.ErrorCode.INVALID_SOLUTION_BODY;

public class AlgorithmNotFoundException extends NotFoundException{

    public AlgorithmNotFoundException() {
        super("알고리즘 타입을 확인해 주세요", INVALID_SOLUTION_BODY);
    }
}

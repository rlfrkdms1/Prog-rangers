package com.prograngers.backend.exception.notfound;

import static com.prograngers.backend.exception.ErrorCode.INVALID_SOLUTION_BODY;

public class DataStructureNotFoundException extends NotFoundException{

    public DataStructureNotFoundException() {
        super("자료구조 타입을 확인해 주세요", INVALID_SOLUTION_BODY);
    }
}

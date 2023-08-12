package com.prograngers.backend.exception.enumtype;

import com.prograngers.backend.exception.notfound.NotFoundException;

import static com.prograngers.backend.exception.ErrorCode.DATASTRUCTURE_NOT_EXISTS;
import static com.prograngers.backend.exception.ErrorCode.INVALID_SOLUTION_BODY;

public class DataStructureNotFoundException extends EnumTypeException {

    public DataStructureNotFoundException() {
        super(DATASTRUCTURE_NOT_EXISTS, "자료구조 타입을 확인해 주세요");
    }
}

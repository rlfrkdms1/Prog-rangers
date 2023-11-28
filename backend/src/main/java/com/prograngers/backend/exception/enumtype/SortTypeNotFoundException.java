package com.prograngers.backend.exception.enumtype;

import static com.prograngers.backend.exception.ErrorCode.SORT_TYPE_NOT_EXISTS;

public class SortTypeNotFoundException extends EnumTypeException {

    public SortTypeNotFoundException() {
        super(SORT_TYPE_NOT_EXISTS, "정렬 타입을 확인해 주세요");
    }
}

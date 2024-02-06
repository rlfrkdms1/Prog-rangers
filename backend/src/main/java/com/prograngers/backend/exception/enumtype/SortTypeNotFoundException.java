package com.prograngers.backend.exception.enumtype;

import static com.prograngers.backend.exception.errorcode.CommonErrorCode.SORT_TYPE_NOT_EXISTS;

public class SortTypeNotFoundException extends EnumTypeException {

    public SortTypeNotFoundException() {
        super(SORT_TYPE_NOT_EXISTS);
    }
}

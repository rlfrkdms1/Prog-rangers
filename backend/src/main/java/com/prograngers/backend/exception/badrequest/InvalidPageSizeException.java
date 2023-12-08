package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCode.PAGE_SIZE_UNDER_THAN_ONE;

public class InvalidPageSizeException extends InvalidValueException{
    public InvalidPageSizeException() {
        super(PAGE_SIZE_UNDER_THAN_ONE, "page의 size는 1이상이어야 합니다.");
    }
}

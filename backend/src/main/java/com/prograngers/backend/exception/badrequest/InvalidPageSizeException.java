package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCode.PAGE_SIZE_MUST_NUMBER_OVER_THAN_ONE;

public class InvalidPageSizeException extends InvalidValueException{
    public InvalidPageSizeException() {
        super(PAGE_SIZE_MUST_NUMBER_OVER_THAN_ONE, "page의 size는 0이상의 숫자형태여야 합니다.");
    }
}

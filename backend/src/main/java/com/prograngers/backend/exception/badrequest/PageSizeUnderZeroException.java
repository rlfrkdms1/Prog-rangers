package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCode.PAGE_SIZE_UNDER_ZERO;

public class PageSizeUnderZeroException extends InvalidValueException{
    public PageSizeUnderZeroException() {
        super(PAGE_SIZE_UNDER_ZERO, "page의 size는 0이상의 숫자형태여야 합니다.");
    }
}

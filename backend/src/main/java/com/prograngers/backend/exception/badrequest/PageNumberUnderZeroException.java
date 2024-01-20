package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCode.PAGE_NUMBER_UNDER_ZERO;

public class PageNumberUnderZeroException extends InvalidValueException {
    public PageNumberUnderZeroException() {
        super(PAGE_NUMBER_UNDER_ZERO, "페이지 번호는 0이상이어야 합니다.");
    }
}

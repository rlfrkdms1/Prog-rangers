package com.prograngers.backend.exception.badrequest.invalidvalue;

import static com.prograngers.backend.exception.errorcode.CommonErrorCode.PAGE_NUMBER_UNDER_ZERO;

public class PageNumberUnderZeroException extends InvalidValueException {
    public PageNumberUnderZeroException() {
        super(PAGE_NUMBER_UNDER_ZERO);
    }
}

package com.prograngers.backend.exception.badrequest.invalidvalue;

import static com.prograngers.backend.exception.errorcode.CommonErrorCode.PAGE_SIZE_MUST_NUMBER_OVER_THAN_ONE;

public class InvalidPageSizeException extends InvalidValueException {
    public InvalidPageSizeException() {
        super(PAGE_SIZE_MUST_NUMBER_OVER_THAN_ONE);
    }
}

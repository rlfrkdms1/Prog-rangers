package com.prograngers.backend.exception.badrequest.invalidvalue;

import static com.prograngers.backend.exception.ErrorCode.INVALID_PAGE_NUMBER;

public class InvalidPageNumberException extends InvalidValueException {
    public InvalidPageNumberException() {
        super(INVALID_PAGE_NUMBER);
    }
}

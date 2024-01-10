package com.prograngers.backend.exception.badrequest.invalidvalue;

import static com.prograngers.backend.exception.errorcode.CommonErrorCode.INVALID_PARENT;

public class InvalidParentException extends InvalidValueException {
    public InvalidParentException() {
        super(INVALID_PARENT);
    }
}

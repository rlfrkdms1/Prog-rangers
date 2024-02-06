package com.prograngers.backend.exception.badrequest.invalidvalue;

import static com.prograngers.backend.exception.errorcode.CommonErrorCode.INVALID_CODE_LINE_NUMBER;


public class InvalidCodeLIneNumberException extends InvalidValueException {
    public InvalidCodeLIneNumberException() {
        super(INVALID_CODE_LINE_NUMBER);
    }
}

package com.prograngers.backend.exception.badrequest.invalidvalue;

import static com.prograngers.backend.exception.ErrorCode.DIFFERENT_CODE_LINE_NUMBER;


public class InvalidCodeLIneNumberException extends InvalidValueException {
    public InvalidCodeLIneNumberException() {
        super(DIFFERENT_CODE_LINE_NUMBER);
    }
}

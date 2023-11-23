package com.prograngers.backend.exception.badrequest.invalidvalue;

import static com.prograngers.backend.exception.ErrorCode.NOT_EXIST_OLD_PASSWORD;

public class NotExistOldPasswordException extends InvalidValueException {
    public NotExistOldPasswordException() {
        super(NOT_EXIST_OLD_PASSWORD);
    }
}

package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.errorcode.AuthErrorCode.INCORRECT_PASSWORD;

public class IncorrectPasswordException extends UnAuthorizationException {
    public IncorrectPasswordException() {
        super(INCORRECT_PASSWORD);
    }
}

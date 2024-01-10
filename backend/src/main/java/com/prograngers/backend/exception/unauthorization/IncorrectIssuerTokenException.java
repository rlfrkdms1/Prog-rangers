package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.errorcode.AuthErrorCode.NOT_PROGRANGERS_TOKEN;

public class IncorrectIssuerTokenException extends UnAuthorizationException {
    public IncorrectIssuerTokenException() {
        super(NOT_PROGRANGERS_TOKEN);
    }
}

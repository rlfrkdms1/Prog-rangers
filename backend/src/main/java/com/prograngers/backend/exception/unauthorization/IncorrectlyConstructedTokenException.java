package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.errorcode.AuthErrorCode.INCORRECTLY_CONSTRUCTED_TOKEN;

public class IncorrectlyConstructedTokenException extends UnAuthorizationException {

    public IncorrectlyConstructedTokenException() {
        super(INCORRECTLY_CONSTRUCTED_TOKEN);
    }
}

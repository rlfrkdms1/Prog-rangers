package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.errorcode.AuthErrorCode.INVALID_ACCESS_TOKEN;

public class InvalidAccessTokenException extends UnAuthorizationException {
    public InvalidAccessTokenException() {
        super(INVALID_ACCESS_TOKEN);
    }
}

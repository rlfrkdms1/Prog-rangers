package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.errorcode.AuthErrorCode.EXPIRED_TOKEN;

public class ExpiredTokenException extends UnAuthorizationException {
    public ExpiredTokenException() {
        super(EXPIRED_TOKEN);
    }
}

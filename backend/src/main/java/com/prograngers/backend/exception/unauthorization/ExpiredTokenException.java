package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCode.EXPIRED_TOKEN;

public class ExpiredTokenException extends UnAuthorizationException {
    public ExpiredTokenException() {
        super(EXPIRED_TOKEN);
    }
}

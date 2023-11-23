package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCode.UNSUPPORTED_TOKEN;

public class UnsupportedTokenException extends UnAuthorizationException {
    public UnsupportedTokenException() {
        super(UNSUPPORTED_TOKEN);
    }
}

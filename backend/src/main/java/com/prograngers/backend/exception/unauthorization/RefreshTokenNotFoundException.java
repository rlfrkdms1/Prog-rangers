package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.errorcode.AuthErrorCode.NOT_FOUND_REFRESH_TOKEN;

public class RefreshTokenNotFoundException extends UnAuthorizationException {
    public RefreshTokenNotFoundException() {
        super(NOT_FOUND_REFRESH_TOKEN);
    }
}

package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.errorcode.AuthErrorCode.NOT_EXIST_REFRESH_TOKEN;

public class NotExistRefreshTokenException extends UnAuthorizationException {
    public NotExistRefreshTokenException() {
        super(NOT_EXIST_REFRESH_TOKEN);
    }
}

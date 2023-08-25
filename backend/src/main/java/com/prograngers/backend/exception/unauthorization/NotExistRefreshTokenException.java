package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCode.NOT_EXIST_REFRESH_TOKEN;

public class NotExistRefreshTokenException extends UnAuthorizationException {
    public NotExistRefreshTokenException() {
        super(NOT_EXIST_REFRESH_TOKEN, "REFRESH 토큰이 존재하지 않습니다.");
    }
}

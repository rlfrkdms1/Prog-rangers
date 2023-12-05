package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCode.INVALID_ACCESS_TOKEN;

public class InvalidAccessTokenException extends UnAuthorizationException {
    public InvalidAccessTokenException() {
        super(INVALID_ACCESS_TOKEN, "유효하지 않은 토큰 값입니다.");
    }
}

package com.prograngers.backend.exception.unauthorization;

import com.prograngers.backend.exception.ErrorCode;

import static com.prograngers.backend.exception.ErrorCode.NOT_EXIST_REFRESH_TOKEN;

public class RefreshTokenNotExistException extends UnAuthorizationException{
    public RefreshTokenNotExistException(ErrorCode errorCode, String message) {
        super(NOT_EXIST_REFRESH_TOKEN, "refresh 토큰이 존재하지 않습니다.");
    }
}

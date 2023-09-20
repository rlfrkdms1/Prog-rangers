package com.prograngers.backend.exception.unauthorization;

import com.prograngers.backend.exception.ErrorCode;

public class RefreshTokenNotFoundException extends UnAuthorizationException {
    public RefreshTokenNotFoundException() {
        super(ErrorCode.NOT_FOUND_REFRESH_TOKEN, "유효한 refresh token이 존재하지 않습니다.");
    }
}

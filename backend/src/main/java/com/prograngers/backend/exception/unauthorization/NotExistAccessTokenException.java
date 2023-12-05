package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCode.NOT_EXIST_ACCESS_TOKEN;

public class NotExistAccessTokenException extends UnAuthorizationException {
    public NotExistAccessTokenException() {
        super(NOT_EXIST_ACCESS_TOKEN, "ACCESS 토큰이 존재하지 않습니다.");
    }
}

package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCodeBefore.EXPIRED_TOKEN;

public class ExpiredTokenException extends UnAuthorizationException {
    public ExpiredTokenException() {
        super(EXPIRED_TOKEN, "토큰의 유효기간이 만료되었습니다.");
    }
}

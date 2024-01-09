package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCodeBefore.INCORRECTLY_CONSTRUCTED_TOKEN;

public class IncorrectlyConstructedTokenException extends UnAuthorizationException {

    public IncorrectlyConstructedTokenException() {
        super(INCORRECTLY_CONSTRUCTED_TOKEN, "잘못된 구조의 토큰입니다.");
    }
}

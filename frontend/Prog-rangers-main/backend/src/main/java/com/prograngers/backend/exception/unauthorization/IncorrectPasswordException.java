package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCode.INCORRECT_PASSWORD;

public class IncorrectPasswordException extends UnAuthorizationException {
    public IncorrectPasswordException() {
        super(INCORRECT_PASSWORD, "비밀번호가 일치하지 않습니다.");
    }
}

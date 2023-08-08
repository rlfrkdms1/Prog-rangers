package com.prograngers.backend.exception;

import static com.prograngers.backend.exception.ErrorCode.INVALID_PASSWORD;

public class InvalidPasswordException extends InvalidException{
    public InvalidPasswordException() {
        super(INVALID_PASSWORD, "비밀번호가 일치하지 않습니다.");
    }
}

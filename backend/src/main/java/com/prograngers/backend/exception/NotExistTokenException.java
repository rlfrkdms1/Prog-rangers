package com.prograngers.backend.exception;

import com.prograngers.backend.exception.ErrorCode;
import com.prograngers.backend.exception.UnAuthorizationException;

import static com.prograngers.backend.exception.ErrorCode.NOT_EXIST_TOKEN;

public class NotExistTokenException extends UnAuthorizationException {
    public NotExistTokenException() {
        super(NOT_EXIST_TOKEN, "토큰이 존재하지 않습니다.");
    }
}
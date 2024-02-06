package com.prograngers.backend.exception;

import com.prograngers.backend.exception.errorcode.ErrorCode;

public class UnAuthorizationException extends CustomException {
    public UnAuthorizationException(ErrorCode errorCode) {
        super(errorCode);
    }
}

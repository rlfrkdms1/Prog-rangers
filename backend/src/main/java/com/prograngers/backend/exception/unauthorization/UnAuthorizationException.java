package com.prograngers.backend.exception.unauthorization;

import com.prograngers.backend.exception.CustomException;
import com.prograngers.backend.exception.ErrorCode;

public class UnAuthorizationException extends CustomException {
    public UnAuthorizationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}

package com.prograngers.backend.exception.unauthorization;

import com.prograngers.backend.exception.CustomException;
import com.prograngers.backend.exception.ErrorCodeBefore;

public class UnAuthorizationException extends CustomException {
    public UnAuthorizationException(ErrorCodeBefore errorCodeBefore, String message) {
        super(errorCodeBefore, message);
    }
}

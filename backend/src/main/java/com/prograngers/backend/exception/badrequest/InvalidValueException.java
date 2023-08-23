package com.prograngers.backend.exception.badrequest;

import com.prograngers.backend.exception.CustomException;
import com.prograngers.backend.exception.ErrorCode;

public class InvalidValueException extends CustomException {
    public InvalidValueException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}

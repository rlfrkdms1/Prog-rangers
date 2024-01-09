package com.prograngers.backend.exception.badrequest;

import com.prograngers.backend.exception.CustomException;
import com.prograngers.backend.exception.ErrorCodeBefore;

public class InvalidValueException extends CustomException {
    public InvalidValueException(ErrorCodeBefore errorCodeBefore, String message) {
        super(errorCodeBefore, message);
    }
}

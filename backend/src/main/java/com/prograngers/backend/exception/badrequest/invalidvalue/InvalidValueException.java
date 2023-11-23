package com.prograngers.backend.exception.badrequest.invalidvalue;

import com.prograngers.backend.exception.CustomException;
import com.prograngers.backend.exception.ErrorCode;

public class InvalidValueException extends CustomException {
    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}

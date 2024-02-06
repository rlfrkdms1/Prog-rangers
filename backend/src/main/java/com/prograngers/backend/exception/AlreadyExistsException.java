package com.prograngers.backend.exception;

import com.prograngers.backend.exception.errorcode.ErrorCode;

public class AlreadyExistsException extends CustomException {
    public AlreadyExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}

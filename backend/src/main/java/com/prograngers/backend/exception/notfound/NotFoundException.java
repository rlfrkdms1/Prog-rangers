package com.prograngers.backend.exception.notfound;

import com.prograngers.backend.exception.ErrorCode;

public class NotFoundException extends RuntimeException{
    ErrorCode errorCode;

    public NotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

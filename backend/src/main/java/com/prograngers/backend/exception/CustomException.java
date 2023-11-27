package com.prograngers.backend.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(final ErrorCode errorCode) {
        super(errorCode.getErrorMessage());
        this.errorCode = errorCode;
    }
}

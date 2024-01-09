package com.prograngers.backend.exception;

import lombok.Getter;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCodeBefore errorCodeBefore;

    public CustomException(final ErrorCodeBefore errorCodeBefore, final String message) {
        super(message);
        this.errorCodeBefore = errorCodeBefore;
    }
}

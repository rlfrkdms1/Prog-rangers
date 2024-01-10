package com.prograngers.backend.exception;

import com.prograngers.backend.exception.errorcode.ErrorCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Getter
public class CustomException extends RuntimeException {

    private final ErrorCode errorCode;

    public CustomException(final ErrorCode errorCode) {
        this.errorCode = errorCode;
    }
}

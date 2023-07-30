package com.prograngers.backend.exception.notfound;

import com.prograngers.backend.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundException extends RuntimeException{
    final ErrorCode errorCode;

    public NotFoundException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

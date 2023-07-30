package com.prograngers.backend.exception.enumtype;

import com.prograngers.backend.exception.ErrorCode;
import lombok.Getter;

@Getter
public class EnumTypeException extends RuntimeException {
    final ErrorCode errorCode;

    public EnumTypeException(String message, ErrorCode errorCode) {
        super(message);
        this.errorCode = errorCode;
    }
}

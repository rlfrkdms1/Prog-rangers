package com.prograngers.backend.exception;

import com.prograngers.backend.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundException extends CustomException {

    public NotFoundException(ErrorCode errorCode) {
        super(errorCode);
    }

}

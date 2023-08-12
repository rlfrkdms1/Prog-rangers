package com.prograngers.backend.exception.notfound;

import com.prograngers.backend.exception.CustomException;
import com.prograngers.backend.exception.ErrorCode;
import lombok.Getter;

@Getter
public class NotFoundException extends CustomException {

    public NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }

}

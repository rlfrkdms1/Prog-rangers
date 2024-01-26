package com.prograngers.backend.exception.badrequest.invalidvalue;

import com.prograngers.backend.exception.CustomException;
import com.prograngers.backend.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class InvalidValueException extends CustomException {
    public InvalidValueException(ErrorCode errorCode) {
        super(errorCode);
    }
}

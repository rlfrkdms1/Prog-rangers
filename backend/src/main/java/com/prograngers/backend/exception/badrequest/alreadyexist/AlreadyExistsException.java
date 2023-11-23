package com.prograngers.backend.exception.badrequest.alreadyexist;

import com.prograngers.backend.exception.CustomException;
import com.prograngers.backend.exception.ErrorCode;

public class AlreadyExistsException extends CustomException {
    public AlreadyExistsException(ErrorCode errorCode) {
        super(errorCode);
    }
}

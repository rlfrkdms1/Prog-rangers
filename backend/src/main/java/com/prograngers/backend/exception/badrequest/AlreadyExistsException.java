package com.prograngers.backend.exception.badrequest;

import com.prograngers.backend.exception.CustomException;
import com.prograngers.backend.exception.ErrorCodeBefore;

public class AlreadyExistsException extends CustomException {
    public AlreadyExistsException(ErrorCodeBefore errorCodeBefore, String message) {
        super(errorCodeBefore, message);
    }
}

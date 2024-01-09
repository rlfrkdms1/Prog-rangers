package com.prograngers.backend.exception.notfound;

import com.prograngers.backend.exception.CustomException;
import com.prograngers.backend.exception.ErrorCodeBefore;
import lombok.Getter;

@Getter
public class NotFoundException extends CustomException {

    public NotFoundException(ErrorCodeBefore errorCodeBefore, String message) {
        super(errorCodeBefore, message);
    }

}

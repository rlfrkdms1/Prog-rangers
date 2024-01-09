package com.prograngers.backend.exception.enumtype;

import com.prograngers.backend.exception.CustomException;
import com.prograngers.backend.exception.ErrorCodeBefore;
import lombok.Getter;

@Getter
public class EnumTypeException extends CustomException {

    public EnumTypeException(ErrorCodeBefore errorCodeBefore, String message) {
        super(errorCodeBefore, message);
    }
}

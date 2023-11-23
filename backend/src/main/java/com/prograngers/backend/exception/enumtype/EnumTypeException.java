package com.prograngers.backend.exception.enumtype;

import com.prograngers.backend.exception.CustomException;
import com.prograngers.backend.exception.ErrorCode;
import lombok.Getter;

@Getter
public class EnumTypeException extends CustomException {

    public EnumTypeException(ErrorCode errorCode) {
        super(errorCode);
    }
}

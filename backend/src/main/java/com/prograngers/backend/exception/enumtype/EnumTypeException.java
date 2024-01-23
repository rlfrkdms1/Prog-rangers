package com.prograngers.backend.exception.enumtype;

import com.prograngers.backend.exception.CustomException;
import com.prograngers.backend.exception.errorcode.ErrorCode;
import lombok.Getter;

@Getter
public class EnumTypeException extends CustomException {

    public EnumTypeException(ErrorCode errorCodeBefore) {
        super(errorCodeBefore);
    }
}

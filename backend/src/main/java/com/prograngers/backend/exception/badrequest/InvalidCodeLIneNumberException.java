package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCode.DIFFERENT_CODE_LINE_NUMBER;


public class InvalidCodeLIneNumberException extends InvalidValueException {
    public InvalidCodeLIneNumberException() {
        super(DIFFERENT_CODE_LINE_NUMBER, "존재하지 않는 코드 줄번호 입니다");
    }
}

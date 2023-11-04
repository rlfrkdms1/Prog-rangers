package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCode.DIFFERENT_SOLUTION;

public class InvalidCodeLIneNumberException extends InvalidValueException {
    public InvalidCodeLIneNumberException() {
        super(DIFFERENT_SOLUTION, "존재하지 않는 코드 줄번호 입니다");
    }
}

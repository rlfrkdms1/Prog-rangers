package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCode.INVALID_PAGE_NUMBER;

public class InvalidPageNumberException extends InvalidValueException {
    public InvalidPageNumberException() {
        super(INVALID_PAGE_NUMBER, "잘못된 페이지 번호입니다.");
    }
}

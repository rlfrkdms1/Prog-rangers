package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCode.INCORRECT_NAVER_CODE;

public class IncorrectCodeInNaverLoginException extends UnAuthorizationException {
    public IncorrectCodeInNaverLoginException() {
        super(INCORRECT_NAVER_CODE, "코드가 일치하지 않습니다.");
    }
}

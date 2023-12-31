package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCode.INCORRECT_NAVER_CODE;

public class IncorrectStateInNaverLoginException extends UnAuthorizationException {
    public IncorrectStateInNaverLoginException() {
        super(INCORRECT_NAVER_CODE, "state가 일치하지 않습니다.");
    }
}

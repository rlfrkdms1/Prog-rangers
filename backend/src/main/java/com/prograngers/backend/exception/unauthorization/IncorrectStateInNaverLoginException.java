package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.errorcode.AuthErrorCode.INCORRECT_NAVER_CODE;

public class IncorrectStateInNaverLoginException extends UnAuthorizationException {
    public IncorrectStateInNaverLoginException() {
        super(INCORRECT_NAVER_CODE);
    }
}

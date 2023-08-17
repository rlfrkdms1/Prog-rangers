package com.prograngers.backend.exception.unauthorization;

import com.prograngers.backend.exception.CustomException;
import com.prograngers.backend.exception.ErrorCode;

import static com.prograngers.backend.exception.ErrorCode.AUTHORIZATION_FAILED;

public class UnAuthorizationException extends CustomException {
    public UnAuthorizationException() {
        super(AUTHORIZATION_FAILED, "권한이 없습니다");
    }
}

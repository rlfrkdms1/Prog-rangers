package com.prograngers.backend.dto;

import com.prograngers.backend.exception.UnAuthorizationException;

import static com.prograngers.backend.exception.ErrorCode.FAIL_TO_ENCODED;

public class FailToEncodeException extends UnAuthorizationException {
    public FailToEncodeException() {
        super(FAIL_TO_ENCODED, "비밀번호를 암호화하는데 실패했습니다.");
    }
}

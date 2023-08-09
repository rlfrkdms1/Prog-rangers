package com.prograngers.backend;

import com.prograngers.backend.exception.UnAuthorizationException;

import static com.prograngers.backend.exception.ErrorCode.FAIL_TO_DECODED;
import static com.prograngers.backend.exception.ErrorCode.FAIL_TO_ENCODED;

public class FailToDecodeException extends UnAuthorizationException {
    public FailToDecodeException() {
        super(FAIL_TO_DECODED, "비밀번호를 복호화하는데 실패했습니다.");
    }
}

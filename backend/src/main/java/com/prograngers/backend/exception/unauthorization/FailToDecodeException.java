package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCodeBefore.FAIL_TO_DECODED;

public class FailToDecodeException extends UnAuthorizationException {
    public FailToDecodeException() {
        super(FAIL_TO_DECODED, "비밀번호를 복호화하는데 실패했습니다.");
    }
}

package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCode.FAILED_SIGNATURE_TOKEN;

public class FailedSignatureTokenException extends UnAuthorizationException {
    public FailedSignatureTokenException() {
        super(FAILED_SIGNATURE_TOKEN, "서명에 실패한 토큰입니다.");
    }
}

package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCode.MISSING_ISSUER_TOKEN;

public class MissingIssuerTokenException extends UnAuthorizationException {
    public MissingIssuerTokenException() {
        super(MISSING_ISSUER_TOKEN, "Issuer가 존재하지 않는 토큰입니다.");
    }
}

package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCode.INVALID_CLAIM_TYPE;

public class InvalidClaimTypeException extends UnAuthorizationException {
    public InvalidClaimTypeException() {
        super(INVALID_CLAIM_TYPE, "토큰의 claim 값은 Long 타입이어야 합니다.");
    }
}

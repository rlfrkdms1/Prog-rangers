package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCode.INVALID_CLAIM_TYPE;

public class InvalidClaimTypeException extends UnAuthorizationException {
    public InvalidClaimTypeException() {
        super(INVALID_CLAIM_TYPE);
    }
}

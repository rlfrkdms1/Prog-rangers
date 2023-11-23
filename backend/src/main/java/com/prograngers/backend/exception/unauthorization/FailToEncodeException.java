package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCode.FAIL_TO_ENCODED;

public class FailToEncodeException extends UnAuthorizationException {
    public FailToEncodeException() {
        super(FAIL_TO_ENCODED);
    }
}

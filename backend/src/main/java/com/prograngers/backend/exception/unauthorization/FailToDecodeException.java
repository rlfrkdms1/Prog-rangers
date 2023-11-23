package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCode.FAIL_TO_DECODED;

public class FailToDecodeException extends UnAuthorizationException {
    public FailToDecodeException() {
        super(FAIL_TO_DECODED);
    }
}

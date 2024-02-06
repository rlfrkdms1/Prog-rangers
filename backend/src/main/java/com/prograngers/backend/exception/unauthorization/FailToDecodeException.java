package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.errorcode.AuthErrorCode.FAILED_TO_DECODE;

public class FailToDecodeException extends UnAuthorizationException {
    public FailToDecodeException() {
        super(FAILED_TO_DECODE);
    }
}

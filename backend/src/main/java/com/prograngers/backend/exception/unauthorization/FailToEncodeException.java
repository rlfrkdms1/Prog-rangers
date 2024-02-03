package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.errorcode.AuthErrorCode.FAILED_TO_ENCODE;

public class FailToEncodeException extends UnAuthorizationException {
    public FailToEncodeException() {
        super(FAILED_TO_ENCODE);
    }
}

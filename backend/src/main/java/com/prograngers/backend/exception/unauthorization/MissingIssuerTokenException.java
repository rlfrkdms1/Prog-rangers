package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.errorcode.AuthErrorCode.MISSING_ISSUER_TOKEN;

public class MissingIssuerTokenException extends UnAuthorizationException {
    public MissingIssuerTokenException() {
        super(MISSING_ISSUER_TOKEN);
    }
}

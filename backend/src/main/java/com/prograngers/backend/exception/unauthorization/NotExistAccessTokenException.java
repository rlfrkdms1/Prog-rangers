package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.errorcode.AuthErrorCode.NOT_EXIST_ACCESS_TOKEN;

public class NotExistAccessTokenException extends UnAuthorizationException {
    public NotExistAccessTokenException() {
        super(NOT_EXIST_ACCESS_TOKEN);
    }
}

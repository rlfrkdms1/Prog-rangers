package com.prograngers.backend.exception;

public class UnAuthorizationException extends CustomException{
    public UnAuthorizationException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}

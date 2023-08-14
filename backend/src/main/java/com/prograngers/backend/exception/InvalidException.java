package com.prograngers.backend.exception;

public class InvalidException extends CustomException {

    public InvalidException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}

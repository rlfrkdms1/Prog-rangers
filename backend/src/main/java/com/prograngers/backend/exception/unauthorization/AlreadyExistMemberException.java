package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCode.ALREADY_EXIST_MEMBER;

public class AlreadyExistMemberException extends UnAuthorizationException {
    public AlreadyExistMemberException() {
        super(ALREADY_EXIST_MEMBER);
    }
}

package com.prograngers.backend.exception.unauthorization;

import com.prograngers.backend.exception.ErrorCode;

public class AlreadyExistMemberException extends UnAuthorizationException {
    public AlreadyExistMemberException() {
        super(ErrorCode.ALREADY_EXIST_MEMBER, "이미 존재하는 회원입니다.");
    }
}

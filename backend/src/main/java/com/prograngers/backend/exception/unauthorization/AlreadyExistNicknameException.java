package com.prograngers.backend.exception.unauthorization;

import com.prograngers.backend.exception.ErrorCodeBefore;

public class AlreadyExistNicknameException extends UnAuthorizationException {
    public AlreadyExistNicknameException() {
        super(ErrorCodeBefore.ALREADY_EXIST_NICKNAME, "이미 존재하는 닉네임입니다.");
    }
}

package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.errorcode.MemberErrorCode.ALREADY_EXIST_NICKNAME;

public class AlreadyExistNicknameException extends UnAuthorizationException {
    public AlreadyExistNicknameException() {
        super(ALREADY_EXIST_NICKNAME);
    }
}

package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCode.ALREADY_EXIST_NICKNAME;

public class AlreadyExistNicknameException extends UnAuthorizationException {
    public AlreadyExistNicknameException() {
        super(ALREADY_EXIST_NICKNAME);
    }
}

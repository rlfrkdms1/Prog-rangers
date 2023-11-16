package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCode.BLANK_NICKNAME;

public class BlankNicknameException extends InvalidValueException {

    public BlankNicknameException() {
        super(BLANK_NICKNAME, "닉네임은 빈칸일 수 없습니다.");
    }
}

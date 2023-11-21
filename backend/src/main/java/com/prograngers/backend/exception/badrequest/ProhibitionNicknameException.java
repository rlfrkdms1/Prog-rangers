package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCode.PROHIBITION_NICKNAME;

public class ProhibitionNicknameException extends InvalidValueException{

    public ProhibitionNicknameException() {
        super(PROHIBITION_NICKNAME, "유요한 사용자 닉네임이 아닙니다.");
    }

}

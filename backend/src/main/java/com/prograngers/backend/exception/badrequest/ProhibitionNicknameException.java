package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCode.PROHIBITION_NICKNAME;

public class ProhibitionNicknameException extends InvalidValueException{

    public ProhibitionNicknameException() {
        super(PROHIBITION_NICKNAME, "사용할 수 없는 사용자 닉네임입니다.");
    }

}

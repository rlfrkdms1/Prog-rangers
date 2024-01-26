package com.prograngers.backend.exception.badrequest.invalidvalue;

import static com.prograngers.backend.exception.errorcode.MemberErrorCode.PROHIBITION_NICKNAME;

public class ProhibitionNicknameException extends InvalidValueException {

    public ProhibitionNicknameException() {
        super(PROHIBITION_NICKNAME);
    }

}

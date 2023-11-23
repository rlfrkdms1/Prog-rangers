package com.prograngers.backend.exception.badrequest.invalidvalue;

import static com.prograngers.backend.exception.ErrorCode.BLANK_NICKNAME;

public class BlankNicknameException extends InvalidValueException {

    public BlankNicknameException() {
        super(BLANK_NICKNAME);
    }
}

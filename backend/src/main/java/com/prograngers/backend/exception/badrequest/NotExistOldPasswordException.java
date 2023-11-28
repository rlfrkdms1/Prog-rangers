package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCode.NOT_EXIST_OLD_PASSWORD;

public class NotExistOldPasswordException extends InvalidValueException {
    public NotExistOldPasswordException() {
        super(NOT_EXIST_OLD_PASSWORD, "비밀번호 변경시, 기존 비밀번호 또한 입력해야합니다.");
    }
}

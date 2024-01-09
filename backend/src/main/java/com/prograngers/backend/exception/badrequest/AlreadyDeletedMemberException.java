package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCodeBefore.ALREADY_DELETED_MEMBER;

public class AlreadyDeletedMemberException extends InvalidValueException {

    public AlreadyDeletedMemberException() {
        super(ALREADY_DELETED_MEMBER, "이미 탈퇴한 회원 입니다.");
    }
}

package com.prograngers.backend.exception.badrequest.invalidvalue;

import static com.prograngers.backend.exception.errorcode.MemberErrorCode.ALREADY_DELETED_MEMBER;

public class AlreadyDeletedMemberException extends InvalidValueException {

    public AlreadyDeletedMemberException() {
        super(ALREADY_DELETED_MEMBER);
    }
}

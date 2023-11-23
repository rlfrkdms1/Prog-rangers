package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCode.DELETED_MEMBER;

public class DeletedMemberException extends UnAuthorizationException {
    public DeletedMemberException() {
        super(DELETED_MEMBER);
    }
}

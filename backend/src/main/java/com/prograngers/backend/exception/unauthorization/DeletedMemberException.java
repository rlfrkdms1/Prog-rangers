package com.prograngers.backend.exception.unauthorization;

import com.prograngers.backend.exception.ErrorCode;

public class DeletedMemberException extends UnAuthorizationException {
    public DeletedMemberException() {
        super(ErrorCode.DELETED_MEMBER, "탈퇴한 사용자입니다.");
    }
}

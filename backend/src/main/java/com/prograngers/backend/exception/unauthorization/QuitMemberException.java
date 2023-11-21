package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCode.QUIT_MEMBER;

public class QuitMemberException extends UnAuthorizationException{
    public QuitMemberException() {
        super(QUIT_MEMBER, "탈퇴한 사용자의 접근입니다.");
    }
}

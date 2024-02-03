package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.errorcode.MemberErrorCode.QUIT_MEMBER;

public class QuitMemberException extends UnAuthorizationException {
    public QuitMemberException() {
        super(QUIT_MEMBER);
    }
}

package com.prograngers.backend.exception.unauthorization;

import static com.prograngers.backend.exception.ErrorCode.UNAUTHORIZED_MEMBER;

public class MemberUnAuthorizedException extends UnAuthorizationException {
    public MemberUnAuthorizedException() {
        super(UNAUTHORIZED_MEMBER, "인가되지 않은 회원입니다");
    }
}

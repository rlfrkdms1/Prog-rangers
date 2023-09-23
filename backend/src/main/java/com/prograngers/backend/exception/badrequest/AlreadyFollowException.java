package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCode.ALREADY_FOLLOW;

public class AlreadyFollowException extends AlreadyExistsException{
    public AlreadyFollowException() {
        super(ALREADY_FOLLOW, "이미 팔로우하고 있는 회원입니다.");
    }
}

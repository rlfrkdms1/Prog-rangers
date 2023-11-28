package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCode.ALREADY_FOLLOWING;

public class AlreadyFollowingException extends AlreadyExistsException {
    public AlreadyFollowingException() {
        super(ALREADY_FOLLOWING, "이미 팔로우하고 있는 회원입니다.");
    }
}

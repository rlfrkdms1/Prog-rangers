package com.prograngers.backend.exception.notfound;

import static com.prograngers.backend.exception.ErrorCode.NOT_FOUND_FOLLOW;

public class FollowNotFoundException extends NotFoundException {

    public FollowNotFoundException() {
        super(NOT_FOUND_FOLLOW, "팔로우 하지 않은 회원입니다.");
    }
}

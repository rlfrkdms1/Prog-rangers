package com.prograngers.backend.exception.notfound;

import static com.prograngers.backend.exception.ErrorCode.NOT_FOUND_FOLLOW;

public class FollowNotFoundException extends NotFoundException {

    public FollowNotFoundException() {
        super(NOT_FOUND_FOLLOW);
    }
}

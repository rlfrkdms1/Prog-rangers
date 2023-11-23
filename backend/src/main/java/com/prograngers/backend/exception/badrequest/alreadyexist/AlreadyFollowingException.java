package com.prograngers.backend.exception.badrequest.alreadyexist;

import static com.prograngers.backend.exception.ErrorCode.ALREADY_FOLLOWING;

public class AlreadyFollowingException extends AlreadyExistsException {
    public AlreadyFollowingException() {
        super(ALREADY_FOLLOWING);
    }
}

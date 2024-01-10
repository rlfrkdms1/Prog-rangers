package com.prograngers.backend.exception.badrequest.alreadyexist;

import static com.prograngers.backend.exception.errorcode.FollowErrorCode.ALREADY_FOLLOWING;

import com.prograngers.backend.exception.badrequest.alreadyexist.AlreadyExistsException;

public class AlreadyFollowingException extends AlreadyExistsException {
    public AlreadyFollowingException() {
        super(ALREADY_FOLLOWING);
    }
}

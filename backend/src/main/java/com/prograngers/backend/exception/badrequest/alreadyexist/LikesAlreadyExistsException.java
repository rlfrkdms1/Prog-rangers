package com.prograngers.backend.exception.badrequest.alreadyexist;

import static com.prograngers.backend.exception.errorcode.LikeErrorCode.ALREADY_EXISTS_LIKE;

import com.prograngers.backend.exception.badrequest.alreadyexist.AlreadyExistsException;

public class LikesAlreadyExistsException extends AlreadyExistsException {

    public LikesAlreadyExistsException() {
        super(ALREADY_EXISTS_LIKE);
    }
}

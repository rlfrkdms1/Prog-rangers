package com.prograngers.backend.exception.badrequest.alreadyexist;

import static com.prograngers.backend.exception.ErrorCode.ALREADY_EXISTS_LIKE;

public class LikesAlreadyExistsException extends AlreadyExistsException {

    public LikesAlreadyExistsException() {
        super(ALREADY_EXISTS_LIKE);
    }
}

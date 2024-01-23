package com.prograngers.backend.exception.notfound;

import static com.prograngers.backend.exception.errorcode.LikeErrorCode.NOT_FOUND_LIKE;

public class LikesNotFoundException extends NotFoundException {
    public LikesNotFoundException() {
        super(NOT_FOUND_LIKE);
    }
}

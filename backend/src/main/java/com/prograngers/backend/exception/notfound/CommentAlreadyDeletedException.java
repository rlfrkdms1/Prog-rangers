package com.prograngers.backend.exception.notfound;

import static com.prograngers.backend.exception.ErrorCode.COMMENT_ALREADY_DELETED;

public class CommentAlreadyDeletedException extends NotFoundException {
    public CommentAlreadyDeletedException() {
        super(COMMENT_ALREADY_DELETED);
    }
}

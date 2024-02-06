package com.prograngers.backend.exception.notfound;

import static com.prograngers.backend.exception.errorcode.CommentErrorCode.COMMENT_ALREADY_DELETED;

public class CommentAlreadyDeletedException extends NotFoundException {
    public CommentAlreadyDeletedException() {
        super(COMMENT_ALREADY_DELETED);
    }
}

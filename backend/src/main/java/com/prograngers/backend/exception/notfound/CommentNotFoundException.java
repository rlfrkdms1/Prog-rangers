package com.prograngers.backend.exception.notfound;

import static com.prograngers.backend.exception.ErrorCode.COMMENT_NOT_FOUND;

public class CommentNotFoundException extends NotFoundException {
    public CommentNotFoundException() {
        super(COMMENT_NOT_FOUND);
    }
}

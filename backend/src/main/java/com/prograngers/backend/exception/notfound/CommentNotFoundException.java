package com.prograngers.backend.exception.notfound;

import static com.prograngers.backend.exception.ErrorCode.COMMENT_NOT_EXISTS;

public class CommentNotFoundException extends NotFoundException{

    public CommentNotFoundException() {
        super("댓글을 찾을 수 없습니다.", COMMENT_NOT_EXISTS);
    }
}

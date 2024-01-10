package com.prograngers.backend.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommentErrorCode implements ErrorCode {

    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "댓글을 찾을 수 없습니다"),
    COMMENT_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "삭제된 댓글입니다");

    private final HttpStatus httpStatus;
    private final String message;

}

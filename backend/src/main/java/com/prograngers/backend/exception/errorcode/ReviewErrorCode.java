package com.prograngers.backend.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ReviewErrorCode implements ErrorCode {

    REVIEW_NOT_FOUND(HttpStatus.BAD_REQUEST, "리뷰를 찾을 수 없습니다"),
    REVIEW_ALREADY_DELETED(HttpStatus.BAD_REQUEST, "삭제된 리뷰입니다");

    private final HttpStatus httpStatus;
    private final String message;
}

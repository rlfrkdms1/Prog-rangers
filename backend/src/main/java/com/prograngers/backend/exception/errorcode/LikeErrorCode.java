package com.prograngers.backend.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum LikeErrorCode implements ErrorCode {

    ALREADY_EXISTS_LIKE(HttpStatus.BAD_REQUEST, "이미 좋아요를 누른 풀이입니다"),
    NOT_FOUND_LIKE(HttpStatus.BAD_REQUEST, "존재하지 않는 좋아요입니다");


    private final HttpStatus httpStatus;
    private final String message;
}

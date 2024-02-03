package com.prograngers.backend.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum FollowErrorCode implements ErrorCode {

    ALREADY_FOLLOWING(HttpStatus.BAD_REQUEST, "이미 팔로우하고 있는 회원입니다"),
    NOT_FOUND_FOLLOW(HttpStatus.BAD_REQUEST, "팔로우를 찾을 수 없습니다"),
    SELF_FOLLOW(HttpStatus.BAD_REQUEST, "본인은 팔로우 할 수 없습니다.");


    private final HttpStatus httpStatus;
    private final String message;
}

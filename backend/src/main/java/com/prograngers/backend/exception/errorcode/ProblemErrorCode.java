package com.prograngers.backend.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ProblemErrorCode implements ErrorCode {

    PROBLEM_NOT_FOUND(HttpStatus.BAD_REQUEST, "문제를 찾을 수 없습니다"),
    PROBLEM_LINK_NOT_FOUND(HttpStatus.BAD_REQUEST, "문제 링크를 찾을 수 없습니다");

    private final HttpStatus httpStatus;
    private final String message;

}

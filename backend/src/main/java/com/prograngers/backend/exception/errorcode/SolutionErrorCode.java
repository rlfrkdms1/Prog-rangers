package com.prograngers.backend.exception.errorcode;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.FORBIDDEN;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum SolutionErrorCode implements ErrorCode {


    SOLUTION_NOT_FOUND(BAD_REQUEST, "풀이를 찾을 수 없습니다"),
    ALGORITHM_NOT_EXISTS(BAD_REQUEST, "존재하지 않는 알고리즘입니다"),
    DATA_STRUCTURE_NOT_EXISTS(BAD_REQUEST, "존재하지 않는 자료구조입니다"),
    LANGUAGE_NOT_EXISTS(BAD_REQUEST, "존재하지 않는 언어입니다"),
    PRIVATE_SOLUTION(FORBIDDEN, "풀이 열람 권한이 없습니다");

    private final HttpStatus httpStatus;
    private final String message;
}

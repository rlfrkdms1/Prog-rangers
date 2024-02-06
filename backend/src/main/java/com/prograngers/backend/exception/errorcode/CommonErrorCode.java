package com.prograngers.backend.exception.errorcode;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    SORT_TYPE_NOT_EXISTS(BAD_REQUEST, "지원하지 않는 정렬 방식입니다"),
    TYPE_MISMATCH(BAD_REQUEST, "잘못된 쿼리 스트링 타입입니다"),
    INVALID_PAGE_NUMBER(BAD_REQUEST, "잘못된 페이지 번호입니다"),
    INVALID_PARENT(BAD_REQUEST, "부모가 존재하지 않습니다"),
    DIFFERENT_SOLUTION(BAD_REQUEST, "자식 댓글 또는 자식 리뷰의 풀이가 부모 댓글 또는 리뷰와 다릅니다"),
    INVALID_CODE_LINE_NUMBER(BAD_REQUEST, "유효하지 않은 줄 번호입니다"),
    PAGE_SIZE_MUST_NUMBER_OVER_THAN_ONE(BAD_REQUEST, "page의 size는 0이상의 숫자형태여야 합니다."),
    PAGE_NUMBER_UNDER_ZERO(BAD_REQUEST, "페이지 번호는 0이상이어야 합니다."),
    PAGE_SIZE_OVER_MAX(BAD_REQUEST, "page size의 값은 max값을 넘길 수 없습니다."),
    INVALID_VALUE(BAD_REQUEST, "잘못된 입력값입니다"),
    INTERNAL_SEVER_ERROR(INTERNAL_SERVER_ERROR, "서버 에러가 발생했습니다");

    private final HttpStatus httpStatus;
    private final String message;
}
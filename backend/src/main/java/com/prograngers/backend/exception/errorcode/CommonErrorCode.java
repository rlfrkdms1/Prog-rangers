package com.prograngers.backend.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CommonErrorCode implements ErrorCode {

    SORT_TYPE_NOT_EXISTS(HttpStatus.BAD_REQUEST, "지원하지 않는 정렬 방식입니다"),
    INVALID_PAGE_NUMBER(HttpStatus.BAD_REQUEST, "잘못된 페이지 번호입니다"),
    INVALID_PARENT(HttpStatus.BAD_REQUEST, "부모가 존재하지 않습니다"),
    DIFFERENT_SOLUTION(HttpStatus.BAD_REQUEST, "자식 댓글 또는 자식 리뷰의 풀이가 부모 댓글 또는 리뷰와 다릅니다"),
    INVALID_CODE_LINE_NUMBER(HttpStatus.BAD_REQUEST, "유효하지 않은 줄 번호입니다"),
    PAGE_SIZE_UNDER_THAN_ONE(HttpStatus.BAD_REQUEST, "page size는 1 이상이어야 합니다");

    private final HttpStatus httpStatus;
    private final String message;
}

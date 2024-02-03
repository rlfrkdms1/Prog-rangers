package com.prograngers.backend.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum MemberErrorCode implements ErrorCode {

    MEMBER_NOT_FOUND(HttpStatus.BAD_REQUEST, "회원을 찾을 수 없습니다"),
    ALREADY_EXIST_MEMBER(HttpStatus.BAD_REQUEST, "이미 존재하는 회원입니다"),
    ALREADY_EXIST_NICKNAME(HttpStatus.BAD_REQUEST, "이미 존재하는 닉네임입니다"),
    BLANK_NICKNAME(HttpStatus.BAD_REQUEST, "닉네임은 빈칸일 수 없습니다"),
    NOT_EXIST_OLD_PASSWORD(HttpStatus.BAD_REQUEST, "기존 비밀번호를 입력하지 않았습니다"),

    ALREADY_DELETED_MEMBER(HttpStatus.BAD_REQUEST, "이미 탈퇴한 회원입니다"),
    PROHIBITION_NICKNAME(HttpStatus.BAD_REQUEST, "사용할 수 없는 닉네임입니다"),
    QUIT_MEMBER(HttpStatus.BAD_REQUEST, "탈퇴한 회원의 접근입니다");

    private final HttpStatus httpStatus;
    private final String message;
}

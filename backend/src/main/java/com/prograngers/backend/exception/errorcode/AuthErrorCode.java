package com.prograngers.backend.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum AuthErrorCode implements ErrorCode {

    INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다"),
    EXPIRED_TOKEN(HttpStatus.UNAUTHORIZED, "토큰이 만료되었습니다"),
    FAILED_TO_ENCODE(HttpStatus.UNAUTHORIZED, "비밀번호를 암호화하는데 실패했습니다"),
    FAILED_TO_DECODE(HttpStatus.UNAUTHORIZED, "비밀번호를 복호화하는데 실패했습니다"),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "유효한 refresh token을 찾을 수 없습니다"),
    NOT_EXIST_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "refresh token이 존재하지 않습니다"),
    INCORRECT_PASSWORD(HttpStatus.UNAUTHORIZED, "비밀번호가 일치하지 않습니다"),
    NOT_EXIST_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "access token이 존재하지 않습니다"),
    INCORRECT_NAVER_CODE(HttpStatus.UNAUTHORIZED, "state가 일치하지 않습니다"),
    INVALID_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, "유효하지 않은 토큰입니다"),
    MISSING_ISSUER_TOKEN(HttpStatus.UNAUTHORIZED, "issuer가 존재하지 않는 토큰입니다"),
    NOT_PROGRANGERS_TOKEN(HttpStatus.UNAUTHORIZED, "발급자가 잘못된 토큰입니다"),
    UNSUPPORTED_TOKEN(HttpStatus.UNAUTHORIZED, "지원하지 않는 토큰 형식입니다"),
    INCORRECTLY_CONSTRUCTED_TOKEN(HttpStatus.UNAUTHORIZED, "잘못된 구조의 토큰입니다"),
    FAILED_SIGNATURE_TOKEN(HttpStatus.UNAUTHORIZED, "서명에 실패한 토큰입니다"),
    UNAUTHORIZED_MEMBER(HttpStatus.UNAUTHORIZED, "인가되지 않은 회원입니다"),
    INVALID_CLAIM_TYPE(HttpStatus.UNAUTHORIZED, "토큰의 claim값은 Long 타입이어야 합니다"),
    ;

    private final HttpStatus httpStatus;
    private final String message;

}

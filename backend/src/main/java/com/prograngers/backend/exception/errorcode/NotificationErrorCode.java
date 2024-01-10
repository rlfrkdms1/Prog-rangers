package com.prograngers.backend.exception.errorcode;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum NotificationErrorCode implements ErrorCode {

    INVALID_NOTIFICATION_TYPE(HttpStatus.BAD_REQUEST, "잘못된 알림 유형입니다"),
    SSE_DISCONNECTED(HttpStatus.BAD_REQUEST, "SSE 연결 오류입니다");


    private final HttpStatus httpStatus;
    private final String message;
}

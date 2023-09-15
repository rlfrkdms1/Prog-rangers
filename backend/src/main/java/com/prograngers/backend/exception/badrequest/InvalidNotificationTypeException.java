package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCode.INVALID_NOTIFICATION_TYPE;

public class InvalidNotificationTypeException extends InvalidValueException {
    public InvalidNotificationTypeException() {
        super(INVALID_NOTIFICATION_TYPE, "잘못된 알림 유형입니다.");
    }
}

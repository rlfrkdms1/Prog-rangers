package com.prograngers.backend.exception.badrequest.invalidvalue;

import static com.prograngers.backend.exception.ErrorCode.INVALID_NOTIFICATION_TYPE;

public class InvalidNotificationTypeException extends InvalidValueException {
    public InvalidNotificationTypeException() {
        super(INVALID_NOTIFICATION_TYPE);
    }
}

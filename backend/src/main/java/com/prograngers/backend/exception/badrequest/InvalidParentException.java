package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCode.INVALID_PARENT;

public class InvalidParentException extends InvalidValueException {
    public InvalidParentException() {
        super(INVALID_PARENT, "부모가 존재하지 않습니다");
    }
}

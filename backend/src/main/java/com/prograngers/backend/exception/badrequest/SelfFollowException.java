package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCode.SELF_FOLLOW;

public class SelfFollowException extends InvalidValueException{
    public SelfFollowException() {
        super(SELF_FOLLOW, "본인은 팔로우 할 수 없습니다.");
    }
}

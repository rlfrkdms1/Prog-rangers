package com.prograngers.backend.exception.badrequest.invalidvalue;

import static com.prograngers.backend.exception.errorcode.FollowErrorCode.SELF_FOLLOW;

public class SelfFollowException extends InvalidValueException{
    public SelfFollowException() {
        super(SELF_FOLLOW);
    }
}

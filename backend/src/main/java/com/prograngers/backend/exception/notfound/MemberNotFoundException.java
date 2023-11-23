package com.prograngers.backend.exception.notfound;

import static com.prograngers.backend.exception.ErrorCode.MEMBER_NOT_FOUND;

public class MemberNotFoundException extends NotFoundException {
    public MemberNotFoundException() {
        super(MEMBER_NOT_FOUND);
    }
}

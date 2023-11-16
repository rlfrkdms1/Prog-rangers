package com.prograngers.backend.exception.notfound;

import static com.prograngers.backend.exception.ErrorCode.NOT_FOUND_LIKE;

public class LikesNotFoundException extends NotFoundException {
    public LikesNotFoundException() {
        super(NOT_FOUND_LIKE, "좋아요를 찾을  수 없습니다");
    }
}

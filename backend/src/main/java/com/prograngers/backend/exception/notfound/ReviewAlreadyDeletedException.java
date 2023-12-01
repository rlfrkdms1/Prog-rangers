package com.prograngers.backend.exception.notfound;


import static com.prograngers.backend.exception.ErrorCode.REVIEW_ALREADY_DELETED;

public class ReviewAlreadyDeletedException extends NotFoundException {
    public ReviewAlreadyDeletedException() {
        super(REVIEW_ALREADY_DELETED, "이미 삭제된 리뷰입니다");
    }
}

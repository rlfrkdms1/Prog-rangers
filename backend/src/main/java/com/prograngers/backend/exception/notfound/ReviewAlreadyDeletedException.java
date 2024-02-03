package com.prograngers.backend.exception.notfound;


import static com.prograngers.backend.exception.errorcode.ReviewErrorCode.REVIEW_ALREADY_DELETED;

public class ReviewAlreadyDeletedException extends NotFoundException {
    public ReviewAlreadyDeletedException() {
        super(REVIEW_ALREADY_DELETED);
    }
}

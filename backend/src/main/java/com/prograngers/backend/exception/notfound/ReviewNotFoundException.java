package com.prograngers.backend.exception.notfound;


import static com.prograngers.backend.exception.ErrorCode.REVIEW_NOT_FOUND;

public class ReviewNotFoundException extends NotFoundException {
    public ReviewNotFoundException() {
        super(REVIEW_NOT_FOUND);
    }
}

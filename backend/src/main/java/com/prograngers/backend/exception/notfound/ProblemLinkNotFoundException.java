package com.prograngers.backend.exception.notfound;

import static com.prograngers.backend.exception.ErrorCode.PROBLEM_LINK_NOT_FOUND;

public class ProblemLinkNotFoundException extends NotFoundException {

    public ProblemLinkNotFoundException() {
        super("문제 링크가 유효하지 않습니다", PROBLEM_LINK_NOT_FOUND);
    }
}

package com.prograngers.backend.exception.notfound;

import static com.prograngers.backend.exception.ErrorCodeBefore.PROBLEM_LINK_NOT_FOUND;

public class ProblemLinkNotFoundException extends NotFoundException {

    public ProblemLinkNotFoundException() {
        super(PROBLEM_LINK_NOT_FOUND, "문제 링크가 유효하지 않습니다");
    }
}

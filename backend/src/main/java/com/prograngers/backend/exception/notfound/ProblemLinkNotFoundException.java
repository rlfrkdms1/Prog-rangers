package com.prograngers.backend.exception.notfound;

import static com.prograngers.backend.exception.errorcode.ProblemErrorCode.PROBLEM_LINK_NOT_FOUND;

public class ProblemLinkNotFoundException extends NotFoundException {

    public ProblemLinkNotFoundException() {
        super(PROBLEM_LINK_NOT_FOUND);
    }
}

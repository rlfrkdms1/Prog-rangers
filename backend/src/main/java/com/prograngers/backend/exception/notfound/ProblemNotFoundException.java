package com.prograngers.backend.exception.notfound;


import static com.prograngers.backend.exception.ErrorCode.PROBLEM_NOT_FOUND;

public class ProblemNotFoundException extends NotFoundException {

    public ProblemNotFoundException() {
        super(PROBLEM_NOT_FOUND);
    }
}

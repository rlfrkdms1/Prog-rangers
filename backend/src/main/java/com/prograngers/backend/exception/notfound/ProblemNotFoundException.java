package com.prograngers.backend.exception.notfound;


import static com.prograngers.backend.exception.ErrorCode.PROBLEM_NOT_FOUND;

public class ProblemNotFoundException extends NotFoundException {

    public ProblemNotFoundException() {
        super("문제를 찾을 수 없습니다.", PROBLEM_NOT_FOUND);
    }
}

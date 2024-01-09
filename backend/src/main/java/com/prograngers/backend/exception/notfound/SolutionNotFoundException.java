package com.prograngers.backend.exception.notfound;

import static com.prograngers.backend.exception.ErrorCodeBefore.SOLUTION_NOT_FOUND;

public class SolutionNotFoundException extends NotFoundException {

    public SolutionNotFoundException() {
        super(SOLUTION_NOT_FOUND, "풀이를 찾을 수 없습니다.");
    }
}

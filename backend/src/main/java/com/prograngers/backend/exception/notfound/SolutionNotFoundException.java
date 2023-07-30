package com.prograngers.backend.exception.notfound;

import static com.prograngers.backend.exception.ErrorCode.SOLUTION_NOT_FOUND;

public class SolutionNotFoundException extends NotFoundException {

    public SolutionNotFoundException() {
        super("풀이를 찾을 수 없습니다.", SOLUTION_NOT_FOUND);
    }
}

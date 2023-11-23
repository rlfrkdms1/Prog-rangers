package com.prograngers.backend.exception.notfound;

import static com.prograngers.backend.exception.ErrorCode.SOLUTION_NOT_FOUND;

public class SolutionNotFoundException extends NotFoundException {

    public SolutionNotFoundException() {
        super(SOLUTION_NOT_FOUND);
    }
}

package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCode.DIFFERENT_SOLUTION;
import static com.prograngers.backend.exception.ErrorCode.INVALID_PARENT;

public class DifferentSolutionException extends InvalidValueException {
    public DifferentSolutionException() {
        super(DIFFERENT_SOLUTION, "생성하려는 자식과 부모의 풀이가 다릅니다");
    }
}

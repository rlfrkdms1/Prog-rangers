package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCodeBefore.DIFFERENT_SOLUTION;

public class DifferentSolutionException extends InvalidValueException {
    public DifferentSolutionException() {
        super(DIFFERENT_SOLUTION, "생성하려는 자식과 부모의 풀이가 다릅니다");
    }
}

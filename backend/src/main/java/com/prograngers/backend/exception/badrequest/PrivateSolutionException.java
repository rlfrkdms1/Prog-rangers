package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCode.PRIVATE_SOLUTION;

public class PrivateSolutionException extends InvalidValueException {

    public PrivateSolutionException() {
        super(PRIVATE_SOLUTION, "비공개 풀이를 열람할 수 없습니다");
    }
}

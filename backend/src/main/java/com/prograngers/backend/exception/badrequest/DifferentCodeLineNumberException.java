package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCodeBefore.DIFFERENT_SOLUTION;

public class DifferentCodeLineNumberException extends InvalidValueException {
    public DifferentCodeLineNumberException() {
        super(DIFFERENT_SOLUTION, "부모리뷰와 자식리뷰의 코드 줄 번호가 일치하지 않습니다");
    }
}

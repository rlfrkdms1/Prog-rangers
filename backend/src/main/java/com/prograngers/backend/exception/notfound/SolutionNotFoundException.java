package com.prograngers.backend.exception.notfound;

import com.prograngers.backend.exception.ErrorCode;

import static com.prograngers.backend.exception.ErrorCode.INVALID_SOLUTION_BODY;

public class SolutionNotFoundException extends NotFoundException{

    public SolutionNotFoundException() {
        super("풀이를 찾을 수 없습니다.", INVALID_SOLUTION_BODY);
    }
}

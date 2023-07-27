package com.prograngers.backend.exception.notfound;

import com.prograngers.backend.exception.ErrorCode;

import static com.prograngers.backend.exception.ErrorCode.INVALID_SOLUTION_BODY;
import static com.prograngers.backend.exception.ErrorCode.SOLUTION_NOT_EXISTS;

public class SolutionNotFoundException extends NotFoundException{

    public SolutionNotFoundException() {
        super("풀이를 찾을 수 없습니다.", SOLUTION_NOT_EXISTS);
    }
}

package com.prograngers.backend.exception.notfound;

import static com.prograngers.backend.exception.ErrorCode.INVALID_SOLUTION_BODY;

public class LevelNotFoundException extends NotFoundException{

    public LevelNotFoundException() {
        super("레벨 타입을 확인해 주세요", INVALID_SOLUTION_BODY);
    }
}

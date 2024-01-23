package com.prograngers.backend.exception.badrequest.invalidvalue;

import static com.prograngers.backend.exception.errorcode.CommonErrorCode.DIFFERENT_SOLUTION;

public class DifferentCodeLineNumberException extends InvalidValueException {
    public DifferentCodeLineNumberException() {
        super(DIFFERENT_SOLUTION);
    }
}

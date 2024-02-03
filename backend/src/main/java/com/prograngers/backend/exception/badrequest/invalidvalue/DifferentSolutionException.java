package com.prograngers.backend.exception.badrequest.invalidvalue;

import static com.prograngers.backend.exception.errorcode.CommonErrorCode.DIFFERENT_SOLUTION;

public class DifferentSolutionException extends InvalidValueException {
    public DifferentSolutionException() {
        super(DIFFERENT_SOLUTION);
    }
}

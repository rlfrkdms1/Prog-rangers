package com.prograngers.backend.exception.badrequest.invalidvalue;

import static com.prograngers.backend.exception.errorcode.SolutionErrorCode.PRIVATE_SOLUTION;

public class PrivateSolutionException extends InvalidValueException {

    public PrivateSolutionException() {
        super(PRIVATE_SOLUTION);
    }
}
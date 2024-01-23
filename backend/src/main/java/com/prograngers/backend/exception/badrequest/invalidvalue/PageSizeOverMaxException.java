package com.prograngers.backend.exception.badrequest.invalidvalue;

import static com.prograngers.backend.exception.errorcode.CommonErrorCode.PAGE_SIZE_OVER_MAX;

public class PageSizeOverMaxException extends InvalidValueException {

    public PageSizeOverMaxException(int maxSize) {
        super(PAGE_SIZE_OVER_MAX);
    }
}

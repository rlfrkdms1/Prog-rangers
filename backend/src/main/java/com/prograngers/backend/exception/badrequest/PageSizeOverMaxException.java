package com.prograngers.backend.exception.badrequest;

import static com.prograngers.backend.exception.ErrorCode.PAGE_SIZE_OVER_MAX;

public class PageSizeOverMaxException extends InvalidValueException {

    public PageSizeOverMaxException(int maxSize) {
        super(PAGE_SIZE_OVER_MAX, String.format("page size의 값은 %d값을 넘길 수 없습니다.", maxSize));
    }
}

package com.prograngers.backend.exception;

import static com.prograngers.backend.exception.ErrorCode.SSE_DISCONNECTED;

public class ServerSentEventConnectException extends CustomException {
    public ServerSentEventConnectException() {
        super(SSE_DISCONNECTED);
    }
}

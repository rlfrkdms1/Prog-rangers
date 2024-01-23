package com.prograngers.backend.controller;

import static com.prograngers.backend.exception.errorcode.CommonErrorCode.INTERNAL_SEVER_ERROR;
import static com.prograngers.backend.exception.errorcode.CommonErrorCode.INVALID_VALUE;
import static com.prograngers.backend.exception.errorcode.CommonErrorCode.TYPE_MISMATCH;

import com.prograngers.backend.dto.error.ErrorResponse;
import com.prograngers.backend.exception.CustomException;
import com.prograngers.backend.exception.errorcode.ErrorCode;
import java.util.List;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.TypeMismatchException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllException(Exception e) {
        return handleExceptionInternal(INTERNAL_SEVER_ERROR);
    }

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus()).body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder().code(errorCode.name()).message(errorCode.getMessage()).build();
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        return handleExceptionInternal(ex, INVALID_VALUE);
    }

    private ResponseEntity<Object> handleExceptionInternal(BindException e, ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus()).body(makeErrorResponse(e, errorCode));
    }

    private ErrorResponse makeErrorResponse(BindException e, ErrorCode errorCode) {
        List<ErrorResponse.ValidationError> validationErrorList = e.getBindingResult().getFieldErrors().stream()
                .map(ErrorResponse.ValidationError::of).collect(Collectors.toList());

        return ErrorResponse.builder().code(errorCode.name()).message(errorCode.getMessage())
                .errors(validationErrorList).build();
    }

    @Override
    protected ResponseEntity<Object> handleTypeMismatch(TypeMismatchException ex, HttpHeaders headers,
                                                        HttpStatusCode status, WebRequest request) {
        return handleExceptionInternal(TYPE_MISMATCH);
    }
}

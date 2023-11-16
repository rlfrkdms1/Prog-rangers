package com.prograngers.backend.controller;

import static com.prograngers.backend.exception.ErrorCode.INVALID_REQUEST_BODY;
import static com.prograngers.backend.exception.ErrorCode.TYPE_MISMATCH;

import com.prograngers.backend.dto.error.ErrorResponse;
import com.prograngers.backend.exception.badrequest.AlreadyExistsException;
import com.prograngers.backend.exception.badrequest.InvalidValueException;
import com.prograngers.backend.exception.enumtype.EnumTypeException;
import com.prograngers.backend.exception.notfound.NotFoundException;
import com.prograngers.backend.exception.unauthorization.UnAuthorizationException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    // Valid를 통과하지 못할 경우 ErrorResponse dto로 해당하는 에러 반환
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> notValidException(MethodArgumentNotValidException exception) {
        List<ErrorResponse> errorList = new ArrayList<>();
        exception.getBindingResult().getAllErrors().stream()
                .map((error) -> ErrorResponse.builder().errorCode(INVALID_REQUEST_BODY)
                        .description(error.getDefaultMessage()).build())
                .forEach((errorResponse) -> errorList.add(errorResponse));
        return new ResponseEntity(errorList, HttpStatus.BAD_REQUEST);
    }

    // 요청한 PathVariable에 대한 데이터가 db에 없는 경우 에러메세지와 함께 NoSuchElementException 던짐
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> notFoundException(NotFoundException exception) {
        String message = exception.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(), message);
        return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(UnAuthorizationException.class)
    public ResponseEntity<ErrorResponse> unAuthorizationException(UnAuthorizationException exception) {
        String message = exception.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(), message);
        return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EnumTypeException.class)
    public ResponseEntity<ErrorResponse> enumTypeException(EnumTypeException exception) {
        String message = exception.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(), message);
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<ErrorResponse> typeMismatchException(MethodArgumentTypeMismatchException exception) {
        String message = "쿼리 스트링 타입을 확인해주세요";
        ErrorResponse errorResponse = new ErrorResponse(TYPE_MISMATCH, message);
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(AlreadyExistsException.class)
    public ResponseEntity<ErrorResponse> alreadyExistsException(AlreadyExistsException exception) {
        String message = exception.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(), message);
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidValueException.class)
    public ResponseEntity<ErrorResponse> invalidValueException(InvalidValueException exception) {
        String message = exception.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(), message);
        return new ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST);
    }
}

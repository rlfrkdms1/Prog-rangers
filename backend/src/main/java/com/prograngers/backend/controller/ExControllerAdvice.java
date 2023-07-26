package com.prograngers.backend.controller;

import com.prograngers.backend.dto.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import java.util.NoSuchElementException;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validEx(MethodArgumentNotValidException exception){
        String message = exception.getBindingResult().getFieldError().getDefaultMessage();
        // 로그 찍히는 것은 확인함
        log.info("MethodArgumentNotValidException 발생 ! : message : {}",message); // 로그까지는 찍히는데 반환이 정상적으로 안됨
        ErrorResponse errorResponse = new ErrorResponse(message);
        // postman에서 errorResponse객체로 응답이 안됨
        return new ResponseEntity(errorResponse,HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> noSuchElementEx(NoSuchElementException exception){
        String message = exception.getMessage();
        log.info("NoSuchElementException 발생! : message : {}",message); // 로그까지는 찍히는데 반환이 정상적으로 안됨
        ErrorResponse errorResponse = new ErrorResponse(message);
        return ResponseEntity.badRequest().body(errorResponse);
    }



}

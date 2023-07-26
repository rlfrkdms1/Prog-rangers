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

    // Valid를 통과하지 못할 경우 ErrorResponse dto로 해당하는 에러 반환
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> validEx(MethodArgumentNotValidException exception){
        String message = exception.getBindingResult().getFieldError().getDefaultMessage();
        // 로그 찍히는 것은 확인함
        log.info("MethodArgumentNotValidException 발생 ! : message : {}",message);
        ErrorResponse errorResponse = new ErrorResponse(message);
        // postman에서 errorResponse객체로 응답이 안됨
        return new ResponseEntity(errorResponse,HttpStatus.BAD_REQUEST);
    }

    // 요청한 PathVariable에 대한 데이터가 db에 없는 경우 에러메세지와 함께 NoSuchElementException 던짐
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> noSuchElementEx(NoSuchElementException exception){
        String message = exception.getMessage();
        log.info("NoSuchElementException 발생! : message : {}",message);
        ErrorResponse errorResponse = new ErrorResponse(message);
        return ResponseEntity.badRequest().body(errorResponse);
    }

}

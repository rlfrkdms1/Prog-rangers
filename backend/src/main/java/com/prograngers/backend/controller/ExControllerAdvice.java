package com.prograngers.backend.controller;

import com.prograngers.backend.dto.ErrorResponse;
import com.prograngers.backend.exception.ErrorCode;
import com.prograngers.backend.exception.enumtype.EnumTypeException;
import com.prograngers.backend.exception.notfound.NotFoundException;
import com.prograngers.backend.exception.notfound.SolutionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RestControllerAdvice
public class ExControllerAdvice {

    // Valid를 통과하지 못할 경우 ErrorResponse dto로 해당하는 에러 반환
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<ErrorResponse>> validEx(MethodArgumentNotValidException exception){
        List<ErrorResponse> errorList = new ArrayList<>();
        List<ObjectError> errors = exception.getBindingResult().getAllErrors();
        for (ObjectError error : errors) {
            ErrorResponse errorResponse = ErrorResponse.builder()
                    .errorCode(ErrorCode.INVALID_SOLUTION_BODY)
                    .descriptions(error.getDefaultMessage())
                    .build();
            errorList.add(errorResponse);
        }
        return new ResponseEntity(errorList,HttpStatus.BAD_REQUEST);
    }

     // 요청한 PathVariable에 대한 데이터가 db에 없는 경우 에러메세지와 함께 NoSuchElementException 던짐
    @ExceptionHandler
    public ResponseEntity<ErrorResponse> noSuchElementEx(NotFoundException exception){
        String message = exception.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(), message);
        return new ResponseEntity(errorResponse,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler
    public ResponseEntity<ErrorResponse> enumTypeEx(EnumTypeException exception){
        String message = exception.getMessage();
        ErrorResponse errorResponse = new ErrorResponse(exception.getErrorCode(), message);
        return new ResponseEntity(errorResponse,HttpStatus.BAD_REQUEST);
    }

}

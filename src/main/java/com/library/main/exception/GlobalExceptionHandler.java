package com.library.main.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<List<ErrorVO>> validationExceptionHandler(ValidationException e){
        return ResponseEntity.badRequest().body(e.getErrorVO());
    }
}

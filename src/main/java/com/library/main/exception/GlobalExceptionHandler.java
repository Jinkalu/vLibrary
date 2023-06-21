package com.library.main.exception;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.http.HttpStatus;
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

    @ExceptionHandler(ExpException.class)
    public ResponseEntity<List<ErrorVO>> handleExpiredJwtException(ExpException ex) {
        // Handle the expired JWT exception
        // You can implement custom logic, such as returning a specific error message or HTTP status code
        // For example:
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ex.getErrors());
    }
}

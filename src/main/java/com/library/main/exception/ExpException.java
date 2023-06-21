package com.library.main.exception;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Header;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
public class ExpException extends ExpiredJwtException {
    private List<ErrorVO> errors;

    public ExpException(Header header, Claims claims, String message,List<ErrorVO> errors) {
        super(header, claims, message);
        this.errors=errors;
    }

    public ExpException(Header header, Claims claims, String message, Throwable cause) {
        super(header, claims, message, cause);
    }



}

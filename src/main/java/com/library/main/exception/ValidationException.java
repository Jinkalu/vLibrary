package com.library.main.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
@Getter
@AllArgsConstructor
public class ValidationException extends RuntimeException{
    private List<ErrorVO> errorVO;
}

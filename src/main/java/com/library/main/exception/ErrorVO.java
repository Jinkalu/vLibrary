package com.library.main.exception;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ErrorVO {
    private String code;
    private String message;

    @Override
    public String toString() {
        return "{ \n" +
                "code : " + code +"\n"+
                "message : " + message +"\n"+
                '}';
    }
}

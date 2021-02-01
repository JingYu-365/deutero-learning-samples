package com.github.labazhang.common.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MicroException extends RuntimeException {
    private Integer code;
    private String msg;
}

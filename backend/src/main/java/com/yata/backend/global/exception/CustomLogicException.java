package com.yata.backend.global.exception;

import lombok.Getter;

public class CustomLogicException extends RuntimeException{
    @Getter
    private ExceptionCode exceptionCode;
    public CustomLogicException(ExceptionCode exceptionCode) {
        super(exceptionCode.getMessage());
        this.exceptionCode = exceptionCode;
    }

}

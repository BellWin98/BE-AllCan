package com.beallcan.global.exception;

import lombok.Getter;

@Getter
public class AllCanException extends RuntimeException{

    private final ErrorCode errorCode;

    public AllCanException(ErrorCode errorCode){
        super(errorCode.getMessage());
        this.errorCode = errorCode;
    }
}

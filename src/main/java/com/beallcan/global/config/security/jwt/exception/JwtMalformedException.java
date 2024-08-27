package com.beallcan.global.config.security.jwt.exception;

import com.beallcan.global.exception.AllCanException;
import com.beallcan.global.exception.ErrorCode;

public class JwtMalformedException extends AllCanException {

    private static final ErrorCode errorCode = ErrorCode.JWT_MALFORMED;

    public JwtMalformedException() {
        super(errorCode);
    }
}

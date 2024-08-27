package com.beallcan.global.config.security.jwt.exception;

import com.beallcan.global.exception.AllCanException;
import com.beallcan.global.exception.ErrorCode;

public class SignatureErrorException extends AllCanException {

    private static final ErrorCode errorCode = ErrorCode.SIGNATURE_VERIFICATION;

    public SignatureErrorException() {
        super(errorCode);
    }
}

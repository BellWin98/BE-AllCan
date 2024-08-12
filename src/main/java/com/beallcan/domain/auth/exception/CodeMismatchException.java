package com.beallcan.domain.auth.exception;

import com.beallcan.global.exception.AllCanException;
import com.beallcan.global.exception.ErrorCode;

public class CodeMismatchException extends AllCanException {

    private static final ErrorCode errorCode = ErrorCode.CODE_MISMATCH;

    public CodeMismatchException() {
        super(errorCode);
    }
}

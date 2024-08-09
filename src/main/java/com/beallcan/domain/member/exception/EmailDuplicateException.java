package com.beallcan.domain.member.exception;

import com.beallcan.global.exception.AllCanException;
import com.beallcan.global.exception.ErrorCode;

public class EmailDuplicateException extends AllCanException {

    private static final ErrorCode errorCode = ErrorCode.EMAIL_DUPLICATE;

    public EmailDuplicateException() {
        super(errorCode);
    }
}

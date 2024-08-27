package com.beallcan.domain.member.exception;

import com.beallcan.global.exception.AllCanException;
import com.beallcan.global.exception.ErrorCode;

public class NicknameDuplicateException extends AllCanException {

    private static final ErrorCode errorCode = ErrorCode.NICKNAME_DUPLICATE;

    public NicknameDuplicateException() {
        super(errorCode);
    }
}

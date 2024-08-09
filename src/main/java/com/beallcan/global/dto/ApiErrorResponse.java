package com.beallcan.global.dto;

import com.beallcan.global.exception.ErrorCode;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

@Getter
public class ApiErrorResponse extends ApiResponse{

    private final String exceptionType;
    private final String errorMessage;

    private ApiErrorResponse(String path,
                             String exceptionType,
                             String errorMessage){
        super(path);
        this.exceptionType = exceptionType;
        this.errorMessage = errorMessage;
    }

    public static ApiErrorResponse of(HttpStatus httpStatus,
                                      String path,
                                      String exceptionType,
                                      String errorMessage){

        return new ApiErrorResponse(path, exceptionType, errorMessage);
    }

    public static ApiErrorResponse of(String path,
                                      String exceptionType,
                                      ErrorCode errorCode){

        return new ApiErrorResponse(path, exceptionType, errorCode.getMessage());
    }

    // dto에서 validation 에러 발생 시 받아주는 메서드
    // Spring이 제공하는 검증 오류를 보관하는 객체
    public static ApiErrorResponse of(String path,
                                      String exceptionType,
                                      BindingResult bindingResult){

        String errorMessage = createErrorMessage(bindingResult);

        return new ApiErrorResponse(path, exceptionType, errorMessage);
    }

    private static String createErrorMessage(BindingResult bindingResult){

        StringBuilder sb = new StringBuilder();
        boolean isFirst = true;
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();
        for (FieldError fieldError : fieldErrors){
            if (!isFirst){
                sb.append(", ");
            } else {
                isFirst = false;
            }
            sb.append("[");
            sb.append(fieldError.getField());
            sb.append("]");
            sb.append(fieldError.getDefaultMessage());
        }

        return sb.toString();
    }
}

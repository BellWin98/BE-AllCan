package com.beallcan.global.exception;

import com.beallcan.global.dto.ApiErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
// @RestController에서 전역적으로 발생하는 예외를 여기서 처리
public class GlobalExceptionHandler {

    private static final String LOG_FORMAT = "Class: {}, Status: {}, Message: {}";

    /**
     * @Valid 또는 @Validated binding error가 발생할 경우
     */
    @ExceptionHandler(BindException.class)
    protected ResponseEntity<ApiErrorResponse> handleBindException(HttpServletRequest request, BindException e){

        HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
        String className = e.getClass().getSimpleName();
        ApiErrorResponse errorResponse = ApiErrorResponse.of(
                request.getServletPath(),
                className,
                e.getBindingResult());

        log.error(LOG_FORMAT, className, httpStatus, errorResponse.getErrorMessage());

        return ResponseEntity
                .status(httpStatus)
                .body(errorResponse);
    }

    /**
     * 지원하지 않는 HTTP method 호출할 경우 발생
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    protected ResponseEntity<ApiErrorResponse>
    handleHttpRequestMethodNotSupportedException(HttpServletRequest request,
                                                 HttpRequestMethodNotSupportedException e){

        HttpStatus httpStatus = HttpStatus.METHOD_NOT_ALLOWED;
        String className = e.getClass().getSimpleName();
        String errorMessage = e.getMessage();

        ApiErrorResponse errorResponse = ApiErrorResponse.of(
                httpStatus,
                request.getServletPath(),
                className,
                errorMessage);

        log.error(LOG_FORMAT, className, httpStatus, errorMessage);

        return ResponseEntity
                .status(httpStatus)
                .body(errorResponse);
    }

    /**
     * 비즈니스 로직 실행 중 오류 발생
     */
    @ExceptionHandler(AllCanException.class)
    protected ResponseEntity<ApiErrorResponse> handleBusinessException(HttpServletRequest request, AllCanException e){


        String className = e.getClass().getSimpleName();
        ErrorCode errorCode = e.getErrorCode();
        String errorMessage = e.getMessage();
        HttpStatus status = errorCode.getHttpStatus();

        log.error(LOG_FORMAT, className, status, errorMessage);

        return ResponseEntity
                .status(status)
                .body(ApiErrorResponse.of(
                        request.getServletPath(),
                        className,
                        errorCode
                ));
    }

    /**
     * 나머지 예외 발생
     */
    @ExceptionHandler(Exception.class)
    protected ResponseEntity<ApiErrorResponse> handleException(HttpServletRequest request, Exception e){

        HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;
        String className = e.getClass().getSimpleName();
        String errorMessage = e.getMessage();

        log.error(LOG_FORMAT, className, httpStatus, errorMessage);

        ApiErrorResponse errorResponse = ApiErrorResponse.of(
                httpStatus,
                request.getServletPath(),
                className,
                errorMessage
        );

        return ResponseEntity
                .status(httpStatus)
                .body(errorResponse);
    }

}

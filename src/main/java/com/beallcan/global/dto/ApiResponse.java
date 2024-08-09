package com.beallcan.global.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

/**
 * ResponseEntity의 Body로 들어갈 부분 정의
 * 응답 성공 시에는 Data가, 예외 발생 시에는 Error 반환
 */

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class ApiResponse {

    private final String path;

    public static ApiResponse of(String path){
        return new ApiResponse(path);
    }
}

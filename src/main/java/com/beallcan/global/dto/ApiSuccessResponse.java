package com.beallcan.global.dto;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ApiSuccessResponse<T> extends ApiResponse {

    private final T data;

    protected ApiSuccessResponse(String path, T data) {
        super(path);
        this.data = data;
    }

    public static <T>ApiSuccessResponse<T> of(String path, T data){
        return new ApiSuccessResponse<>(path, data);
    }

}

package com.beallcan.domain.auth.controller;

import com.beallcan.domain.auth.service.AuthService;
import com.beallcan.global.dto.ApiSuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @GetMapping("/check-email")
    public ResponseEntity<ApiSuccessResponse<Void>> checkEmailDuplicate(
            HttpServletRequest servletRequest,
            @RequestParam("email") String email){

        authService.checkEmailDuplicate(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        servletRequest.getServletPath(),
                        null
                ));
    }

    @GetMapping("/send-code")
    public ResponseEntity<ApiSuccessResponse<Void>> sendCode(
            HttpServletRequest servletRequest,
            @RequestParam("email") String email) {

        authService.sendCode(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        servletRequest.getServletPath(),
                        null
                ));
    }

    @GetMapping("/verify-code")
    public ResponseEntity<ApiSuccessResponse<String>> verifyCode(
            HttpServletRequest servletRequest,
            @RequestParam("email") String email,
            @RequestParam("code") String code) {

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        servletRequest.getServletPath(),
                        authService.verifyCode(email, code)
                ));
    }
}

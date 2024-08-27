package com.beallcan.domain.auth.controller;

import com.beallcan.domain.auth.dto.request.SignUpRequest;
import com.beallcan.domain.auth.service.AuthService;
import com.beallcan.domain.member.dto.response.MemberResponse;
import com.beallcan.global.dto.ApiSuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @Autowired
    public AuthController(final AuthService authService) {
        this.authService = authService;
    }

    @PostMapping
    public ResponseEntity<ApiSuccessResponse<MemberResponse>> signUp(
            HttpServletRequest servletRequest,
            @RequestBody @Valid SignUpRequest signUpRequest
            ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiSuccessResponse.of(
                        servletRequest.getServletPath(),
                        authService.signUp(signUpRequest)
                ));
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

    @GetMapping("/check-nickname")
    public ResponseEntity<ApiSuccessResponse<Void>> checkNicknameDuplicate(
            HttpServletRequest servletRequest,
            @RequestParam("nickname") String nickname){

        authService.checkNicknameDuplicate(nickname);

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

package com.beallcan.domain.auth.controller;

import com.beallcan.domain.auth.service.AuthService;
import com.beallcan.domain.member.service.MemberService;
import com.beallcan.global.dto.ApiSuccessResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;
    private final MemberService memberService;

    @Autowired
    public AuthController(final AuthService authService, final MemberService memberService) {
        this.authService = authService;
        this.memberService = memberService;
    }

    @GetMapping("/check-email")
    public ResponseEntity<ApiSuccessResponse<Void>> checkEmailDuplicate(HttpServletRequest servletRequest,
                                                                        @RequestParam("email") String email){

        memberService.checkEmailDuplicate(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        servletRequest.getServletPath(),
                        null
                ));
    }

    @GetMapping("/send-code")
    public ResponseEntity<ApiSuccessResponse<Void>> sendVerificationCode(HttpServletRequest servletRequest,
                                                                        @RequestParam("email") String email) throws NoSuchAlgorithmException {

        memberService.sendVerificationCode(email);

        return ResponseEntity
                .status(HttpStatus.OK)
                .body(ApiSuccessResponse.of(
                        servletRequest.getServletPath(),
                        null
                ));
    }
}

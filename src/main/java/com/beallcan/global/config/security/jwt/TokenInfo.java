package com.beallcan.global.config.security.jwt;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class TokenInfo {
    private String grantType;
    private String accessToken;
    private long accessTokenExpiration;
    private String refreshToken;
    private long refreshTokenExpiration;
}

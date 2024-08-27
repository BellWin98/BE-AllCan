package com.beallcan.global.config.security.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.beallcan.global.config.security.jwt.exception.JwtExpiredException;
import com.beallcan.global.config.security.jwt.exception.JwtMalformedException;
import com.beallcan.global.config.security.jwt.exception.SignatureErrorException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

import static com.beallcan.global.config.security.jwt.TokenExpiration.ACCESS_TOKEN_EXPIRE_TIME;
import static com.beallcan.global.config.security.jwt.TokenExpiration.REFRESH_TOKEN_EXPIRE_TIME;

@Component
public class JwtProvider {

    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String ROLE_CLAIM = "role";
    private static final String BEARER = "Bearer ";

    // Access / Refresh Token 생성
    public TokenInfo generateToken(String email, String role) {

        Date now = new Date();

        String accessToken = JWT.create()
                .withSubject(ACCESS_TOKEN_SUBJECT)
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + ACCESS_TOKEN_EXPIRE_TIME))
                .withClaim(EMAIL_CLAIM, email)
                .withClaim(ROLE_CLAIM, role)
                .sign(Algorithm.HMAC512(secretKey));

        String refreshToken = JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withIssuedAt(now)
                .withExpiresAt(new Date(now.getTime() + REFRESH_TOKEN_EXPIRE_TIME))
                .sign(Algorithm.HMAC512(secretKey));

        return TokenInfo.builder()
                .grantType(BEARER)
                .accessToken(accessToken)
                .accessTokenExpiration(ACCESS_TOKEN_EXPIRE_TIME)
                .refreshToken(refreshToken)
                .refreshTokenExpiration(REFRESH_TOKEN_EXPIRE_TIME)
                .build();
    }

    // 토큰 정보 검증
    public void validateToken(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
        } catch (TokenExpiredException e){
            throw new JwtExpiredException();
        } catch (SignatureVerificationException e){
            throw new SignatureErrorException();
        } catch (JWTVerificationException e){
            throw new JwtMalformedException();
        }
    }

    // 토큰 분해해서 정보 추출
    public String resolveToken(HttpServletRequest request) {
//        request.getHeader(accessHeader)
        return null;
    }
}

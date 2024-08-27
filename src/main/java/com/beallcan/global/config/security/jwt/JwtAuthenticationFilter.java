package com.beallcan.global.config.security.jwt;

import com.beallcan.domain.member.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    // 아래 URL로 들어오는 요청은 토큰 검증 필터 안탄다.
    private static final String[] NO_CHECK_URLS = {
            "/login",
            "/api/auth/**"
    };

    private final JwtProvider jwtProvider;
    private final MemberRepository memberRepository;
    private final AntPathMatcher pathMatcher = new AntPathMatcher(); // NO_CHECK_URLS로 요청이 들어오면 토큰 검증 필터 통과

    @Override
    protected void doFilterInternal(final HttpServletRequest request,
                                    final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {

        String requestURI = request.getRequestURI();

        for (String urlPattern : NO_CHECK_URLS) {
            if (pathMatcher.match(urlPattern, requestURI)) {
                filterChain.doFilter(request, response);
                return;
            }
        }


    }
}

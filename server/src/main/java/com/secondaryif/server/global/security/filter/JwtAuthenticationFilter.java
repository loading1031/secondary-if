package com.secondaryif.server.global.security.filter;

import com.secondaryif.server.global.security.service.JwtTokenProvider;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import java.io.IOException;

@RequiredArgsConstructor
public class JwtAuthenticationFilter extends GenericFilterBean {
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate; // RedisTemplate 추가

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String token = resolveToken((HttpServletRequest) request);

        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication authentication = jwtTokenProvider.getAuthentication(token);
                String username = authentication.getName();
                String redisToken = redisTemplate.opsForValue().get(username); // Redis에서 JWT 조회

                // Redis의 JWT와 요청의 JWT가 일치하는지 확인
                if (token.equals(redisToken)) {
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                } else {
                    throw new JwtException("유효하지 않은 토큰입니다.");
                }
            }
            chain.doFilter(request, response);
        } catch (JwtException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpServletResponse.getWriter().write("유효하지 않은 토큰입니다.");
        } catch (RuntimeException ex) {
            httpServletResponse.setStatus(HttpServletResponse.SC_GONE);
            httpServletResponse.getWriter().write("만료된 토큰입니다.");
        }

    }

    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
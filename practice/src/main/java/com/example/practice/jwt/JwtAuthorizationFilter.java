package com.example.practice.jwt;

import com.example.practice.model.Member;
import com.example.practice.repository.MemberRespository;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;

/**
 * JWT를 이용한 인증 (인가)
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter {

    private final MemberRespository memberRespository;

    public JwtAuthorizationFilter(
            MemberRespository memberRespository
    ) {
        this.memberRespository = memberRespository;
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain
    ) throws IOException, ServletException {

        String token = null;
        String refreshToken = null;
        try {
            // cookie 에서 JWT token을 가져옵니다.
            token = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(JwtProperties.COOKIE_NAME)).findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);

            refreshToken = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(JwtProperties.REFRESH_COOKIE_NAME)).findFirst()
                    .map(Cookie::getValue)
                    .orElse(null);
        } catch (Exception ignored) {
        }

        if (token != null && JwtUtils.validateToken(token)) {
            try {
                System.out.println("authToken auth");
                // authentication 을 만들어서 SecurityContext에 넣어준다.
                Authentication authentication = this.getUsernamePasswordAuthenticationToken(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                // 실패하는 경우 쿠키를 초기화
                Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, null);
                cookie.setMaxAge(0);
                response.addCookie(cookie);
            }
        }else {
            Member member = memberRespository.findByRefreshValue(refreshToken);
            if(refreshToken != null && JwtUtils.validateToken(refreshToken) && member != null) {
                System.out.println("refreshToken auth");
                token = JwtUtils.createToken(member.getEmail());
                // 쿠키 생성
                Cookie cookie = new Cookie(JwtProperties.COOKIE_NAME, token);
                cookie.setMaxAge(JwtProperties.EXPIRATION_TIME); // 쿠키의 만료시간 설정
                cookie.setPath("/"); // 전송 범위

                response.addCookie(cookie);

                Authentication authentication = this.getUsernamePasswordAuthenticationToken(token);
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        chain.doFilter(request, response);
    }

    /**
     * JWT 토큰으로 User를 찾아서 UsernamePasswordAuthenticationToken를 만들어서 반환한다.
     * User가 없다면 null
     */
    private Authentication getUsernamePasswordAuthenticationToken(String token) {
        // 토큰으로 username 찾아오기
        String email = JwtUtils.getUsername(token);
        if (email != null) {
            Member member = memberRespository.findByEmail(email); // 유저를 유저명으로 찾습니다.
            return new UsernamePasswordAuthenticationToken(
                    member, // principal
                    null,
                    Collections.singleton((GrantedAuthority) () -> member.getAuth().name())
            );
        }
        return null; // 유저가 없으면 NULL
    }
}
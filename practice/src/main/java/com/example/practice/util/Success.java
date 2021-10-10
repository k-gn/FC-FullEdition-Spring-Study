package com.example.practice.util;

import com.example.practice.config.auth.PrincipalDetails;
import com.example.practice.jwt.JwtProperties;
import com.example.practice.jwt.JwtUtils;
import com.example.practice.repository.MemberRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class Success {


    public static void success(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication,
            MemberRespository memberRespository) {

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        String authToken = JwtUtils.createToken(principal.getMember().getEmail());
        String refreshToken = JwtUtils.createRefreshToken();
        makeAuthCookie(response, authToken);
        makeRefreshCookie(response, refreshToken);
        memberRespository.updateRefresh(refreshToken, principal.getMember().getEmail());
    }

    private static void makeAuthCookie(HttpServletResponse response, String token) {
        // 쿠키 생성
        Cookie authCookie = new Cookie(JwtProperties.COOKIE_NAME, token);
        authCookie.setMaxAge(JwtProperties.EXPIRATION_TIME); // 쿠키의 만료시간 설정
        authCookie.setPath("/"); // 전송 범위
        response.addCookie(authCookie);
    }

    private static void makeRefreshCookie(HttpServletResponse response, String token) {
        // 쿠키 생성
        Cookie refreshCookie = new Cookie(JwtProperties.REFRESH_COOKIE_NAME, token);
        refreshCookie.setMaxAge(JwtProperties.REFRESH_EXPIRATION_TIME); // 쿠키의 만료시간 설정
        refreshCookie.setPath("/"); // 전송 범위
        response.addCookie(refreshCookie);
    }
}

package com.example.practice.config.oauth;

import com.example.practice.config.auth.PrincipalDetails;
import com.example.practice.jwt.JwtProperties;
import com.example.practice.jwt.JwtUtils;
import com.example.practice.repository.MemberRespository;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class OAuth2SuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    private final RequestCache requestCache;
    private final MemberRespository memberRespository;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public OAuth2SuccessHandler(RequestCache requestCache, MemberRespository memberRespository) {
        this.requestCache = requestCache;
        this.memberRespository = memberRespository;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {

        PrincipalDetails principal = (PrincipalDetails) authentication.getPrincipal();
        String authToken = JwtUtils.createToken(principal.getMember().getEmail());
        String refreshToken = JwtUtils.createRefreshToken();
        makeAuthCookie(response, authToken);
        makeRefreshCookie(response, refreshToken);
        memberRespository.updateRefresh(refreshToken, principal.getMember().getEmail());

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if(savedRequest != null){
            redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
        }else{
            redirectStrategy.sendRedirect(request, response, "/");
        }
    }

    private void makeAuthCookie(HttpServletResponse response, String token) {
        // 쿠키 생성
        Cookie authCookie = new Cookie(JwtProperties.COOKIE_NAME, token);
        authCookie.setMaxAge(JwtProperties.EXPIRATION_TIME); // 쿠키의 만료시간 설정
        authCookie.setPath("/"); // 전송 범위
        response.addCookie(authCookie);
    }

    private void makeRefreshCookie(HttpServletResponse response, String token) {
        // 쿠키 생성
        Cookie refreshCookie = new Cookie(JwtProperties.REFRESH_COOKIE_NAME, token);
        refreshCookie.setMaxAge(JwtProperties.REFRESH_EXPIRATION_TIME); // 쿠키의 만료시간 설정
        refreshCookie.setPath("/"); // 전송 범위
        response.addCookie(refreshCookie);
    }
}

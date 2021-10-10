package com.example.practice.jwt;

import com.example.practice.config.auth.PrincipalDetails;
import com.example.practice.repository.MemberRespository;
import com.example.practice.util.Script;
import com.example.practice.util.Success;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * JWT를 이용한 로그인 인증
 */
public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final AuthenticationManager authenticationManager;
    private final RequestCache requestCache;
    private final MemberRespository memberRespository;
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, RequestCache requestCache, MemberRespository memberRespository) {
        super(authenticationManager);
        this.authenticationManager = authenticationManager;
        this.requestCache = requestCache;
        this.memberRespository = memberRespository;
    }

    /**
     * 로그인 인증 시도
     */
    @Override
    public Authentication attemptAuthentication(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws AuthenticationException {

        // 로그인할 때 입력한 username과 password를 가지고 authenticationToken를 생성한다.
        UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                request.getParameter("email"),
                request.getParameter("password"),
                new ArrayList<>()
        );
        return authenticationManager.authenticate(authenticationToken);
    }

    /**
     * 인증에 성공했을 때 사용
     * JWT Token을 생성해서 쿠키에 넣는다.
     */
    @Override
    protected void successfulAuthentication(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain chain,
            Authentication authResult
    ) throws IOException, ServletException {

        Success.success(request, response, authResult, memberRespository);

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
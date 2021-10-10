package com.example.practice.config.oauth;

import com.example.practice.config.auth.PrincipalDetails;
import com.example.practice.jwt.JwtProperties;
import com.example.practice.jwt.JwtUtils;
import com.example.practice.repository.MemberRespository;
import com.example.practice.util.Success;
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

        Success.success(request, response, authentication, memberRespository);

        SavedRequest savedRequest = requestCache.getRequest(request, response);
        if(savedRequest != null){
            redirectStrategy.sendRedirect(request, response, savedRequest.getRedirectUrl());
        }else{
            redirectStrategy.sendRedirect(request, response, "/");
        }
    }
}

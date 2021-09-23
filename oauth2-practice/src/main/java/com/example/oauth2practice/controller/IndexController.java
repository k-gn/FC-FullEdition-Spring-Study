package com.example.oauth2practice.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.authentication.OAuth2AuthenticationToken;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.OAuth2RefreshToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping
@RequiredArgsConstructor
public class IndexController {

    //  OAuth2AuthorizedClientService를 이용하여 인증된 사용자의 정보를 활용해서 액세스 토큰과 리프래쉬 토큰을 가져올 수 있다.
    private final OAuth2AuthorizedClientService authorizedClientService;

    @GetMapping("/")
    public OAuth2AuthenticationToken home(final OAuth2AuthenticationToken authentication) {
        // social login 후  OAuth로 인증된 사용자 토큰 확인하기
        System.out.println("authentication : " + authentication);
        return authentication;
    }

    @GetMapping("/index")
    public String index() {
        return "index";
    }

    @GetMapping("/api/token")
    public ResponseEntity<Object> currentUserToken(final OAuth2AuthenticationToken authentication) {
        System.out.println("currentUserToken method - OAuth2AuthenticationToken");
        Map<String, Object> attributes = new HashMap<>();

        OAuth2AuthorizedClient oAuth2AuthorizedClient = authorizedClientService.loadAuthorizedClient(authentication.getAuthorizedClientRegistrationId(), authentication.getName());
        OAuth2AccessToken accessToken = oAuth2AuthorizedClient.getAccessToken();
        OAuth2RefreshToken refreshToken = oAuth2AuthorizedClient.getRefreshToken();

        attributes.put("name", authentication.getName());
        attributes.put("accessToken", accessToken);
        attributes.put("refreshToken", refreshToken);
        return ResponseEntity.ok(attributes);

    }
}

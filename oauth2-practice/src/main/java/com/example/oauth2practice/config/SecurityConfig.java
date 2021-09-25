package com.example.oauth2practice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.oauth2.client.CommonOAuth2Provider;
import org.springframework.security.oauth2.client.InMemoryOAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClientService;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;
import org.springframework.security.oauth2.core.AuthorizationGrantType;
import org.springframework.security.oauth2.core.ClientAuthenticationMethod;
import org.springframework.security.oauth2.core.oidc.IdTokenClaimNames;

import java.util.List;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Environment environment;
    private final String registration = "spring.security.oauth2.client.registration.";
    private final NaverOauth2UserService naverOauth2UserService;
    private final GoogleOAuth2UserService googleOAuth2UserService;

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests(authorize -> authorize
                .antMatchers("/login", "/index").permitAll()
                .anyRequest().authenticated()
            )
//            .oauth2Login(Customizer.withDefaults()); // default
            .oauth2Login(oauth2 -> oauth2 // custom
                .clientRegistrationRepository(clientRegistrationRepository())
                .authorizedClientService(authorizedClientService())
                .userInfoEndpoint( // oauth2 로그인 성공 후 사용자 정보를 가져오는 설정
                    user -> user
                    .oidcUserService(googleOAuth2UserService) // google - OpenID Connect
                    .userService(naverOauth2UserService) // google 외 - OAuth2
                )
            )
        ;
    }
    
    /*
        - ClientRegistrationRepository
            - ClientRegistration 에 대한 정보를 저장하는 저장소
            - Authorization Server 에서 해당 클라이언트의 정보가 필요할 때 사용
            
        - OAuth2AuthorizedClientService
            - 실제 OAuth2 인증을 진행하는 서비스
            - Access Token 에 대한 통신을 담당
            
        - ClientRegistration
            - 접속할 Provider 에 대한 정보를 구현
            
        - DefaultOAuth2UserService
            - 여기서 해당 사용자 계정의 정보를 가져옴 (loadUser)
    */

    // 직접 oauth2 내부 코드 정의해보기
    @Bean
    public OAuth2AuthorizedClientService authorizedClientService() {
        return new InMemoryOAuth2AuthorizedClientService(clientRegistrationRepository());
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        final List<ClientRegistration> clientRegistrations = List.of(
                googleClientRegistration(),
                naverClientRegistration()
        );
        return new InMemoryClientRegistrationRepository(clientRegistrations);
    }

    private ClientRegistration googleClientRegistration() {
        final String clientId = environment.getProperty(registration + "google.client-id");
        final String clientSecret = environment.getProperty(registration + "google.client-secret");

        return CommonOAuth2Provider
                .GOOGLE
                .getBuilder("google")
                .clientId(clientId)
                .clientSecret(clientSecret)
                .scope("openid", "profile", "email")
                .userInfoUri("https://www.googleapis.com/oauth2/v3/userinfo")
                .build();
    }

    private ClientRegistration naverClientRegistration() {
        final String clientId = environment.getProperty(registration + "naver.client-id");
        final String clientSecret = environment.getProperty(registration + "naver.client-secret");
        final String redirectUri = environment.getProperty(registration + "naver.redirect-uri");

        ClientRegistration.Builder builder = getBuilder("naver",
                ClientAuthenticationMethod.CLIENT_SECRET_BASIC, redirectUri);
        builder.scope("name", "email");
        builder.authorizationUri("https://nid.naver.com/oauth2.0/authorize");
        builder.tokenUri("https://nid.naver.com/oauth2.0/token");
        builder.userInfoUri("https://openapi.naver.com/v1/nid/me");
        builder.userNameAttributeName("response");
        builder.clientName("Naver");


        return builder
                .clientId(clientId)
                .clientSecret(clientSecret)
                .build();
    }

    private final ClientRegistration.Builder getBuilder(String registrationId, ClientAuthenticationMethod method,
                                                          String redirectUri) {
        ClientRegistration.Builder builder = ClientRegistration.withRegistrationId(registrationId);
        builder.clientAuthenticationMethod(method);
        builder.authorizationGrantType(AuthorizationGrantType.AUTHORIZATION_CODE);
        builder.redirectUri(redirectUri);
        return builder;
    }
}

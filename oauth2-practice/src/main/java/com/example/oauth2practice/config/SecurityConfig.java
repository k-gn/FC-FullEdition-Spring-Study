package com.example.oauth2practice.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final Environment environment;
    private final String registration = "spring.security.oauth2.client.registration.";

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http
            .authorizeRequests(authorize -> authorize
                .antMatchers("/login", "/index").permitAll()
                .anyRequest().authenticated()
            )
            .oauth2Login(Customizer.withDefaults()); // default
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
    */
}

package com.example.practice.config;

import com.example.practice.config.oauth.OAuth2SuccessHandler;
import com.example.practice.config.oauth.PrincipalOauth2UserService;
import com.example.practice.jwt.JwtAuthenticationFilter;
import com.example.practice.jwt.JwtAuthorizationFilter;
import com.example.practice.jwt.JwtProperties;
import com.example.practice.repository.MemberRespository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.security.servlet.PathRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final MemberRespository memberRespository;
    private final PrincipalOauth2UserService principalOauth2UserService;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();

        http.requestCache().requestCache(requestCache());
        http.exceptionHandling()
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .accessDeniedHandler(jwtAccessDeniedHandler)
        ;

        // jwt filter
        http.addFilterBefore(
                new JwtAuthenticationFilter(this.authenticationManager(), requestCache(), memberRespository),
                UsernamePasswordAuthenticationFilter.class
        ).addFilterBefore(
                new JwtAuthorizationFilter(memberRespository),
                BasicAuthenticationFilter.class
        );

        http.sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        http.authorizeRequests()
                .antMatchers("/", "/home", "/signup").permitAll()
                .anyRequest().authenticated()
        ;

        http.formLogin()
                .loginPage("/login")
                .usernameParameter("email")
                .defaultSuccessUrl("/", true)
                .permitAll()
        ;

        http.logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/")
                .invalidateHttpSession(true)
                .deleteCookies(JwtProperties.COOKIE_NAME, JwtProperties.REFRESH_COOKIE_NAME)
        ;

        http.oauth2Login()
                .loginPage("/login")
                .successHandler(oAuth2SuccessHandler())
                .userInfoEndpoint()
                .userService(principalOauth2UserService)
        ;

    }

    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().requestMatchers(PathRequest.toStaticResources().atCommonLocations());
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RequestCache requestCache() {
        return new HttpSessionRequestCache();
    }

    @Bean
    public AuthenticationSuccessHandler oAuth2SuccessHandler() {
        return new OAuth2SuccessHandler(requestCache(), memberRespository);
    }
}

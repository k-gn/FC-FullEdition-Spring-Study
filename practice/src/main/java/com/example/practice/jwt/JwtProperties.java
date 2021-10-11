package com.example.practice.jwt;

/**
 * JWT 기본 설정값
 */
public class JwtProperties {
    public static final int EXPIRATION_TIME = 60000;
    public static final int REFRESH_EXPIRATION_TIME = 6000000;
    public static final String COOKIE_NAME = "JWT-AUTHENTICATION";
    public static final String REFRESH_COOKIE_NAME = "JWT-AUTHENTICATION-REFRESH";
}
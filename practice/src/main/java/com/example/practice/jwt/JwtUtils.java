package com.example.practice.jwt;

import com.example.practice.model.Member;
import com.nimbusds.jwt.JWT;
import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.util.Pair;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.security.Key;
import java.time.Instant;
import java.util.Date;

@Slf4j
public class JwtUtils {
    /**
     * 토큰에서 username 찾기
     *
     * @param token 토큰
     * @return username
     */
    public static String getUsername(String token) {
        // jwtToken에서 username을 찾습니다.
        return Jwts.parserBuilder()
                .setSigningKeyResolver(SigningKeyResolver.instance) // 비밀키를 찾는 리졸버 등록
                .build()
                .parseClaimsJws(token)// 토큰 검증 부분
                .getBody()
                .getSubject(); // username
    }

    /**
     * user로 토큰 생성
     * HEADER : alg, kid
     * PAYLOAD : sub, iat, exp
     * SIGNATURE : JwtKey.getRandomKey로 구한 Secret Key로 HS512 해시
     *
     * @param email 유저
     * @return jwt token
     */
    public static String createToken(String email) {
        Claims claims = Jwts.claims().setSubject(email); // subject
        Date now = new Date(); // 현재 시간
        Pair<String, Key> key = JwtKey.getRandomKey();
        // JWT Token 생성
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + JwtProperties.EXPIRATION_TIME)) // 토큰 만료 시간 설정
                .setHeaderParam(JwsHeader.KEY_ID, key.getFirst()) // kid
                .signWith(key.getSecond()) // signature
                .compact();
    }

    public static String createRefreshToken(){
        Claims claims = Jwts.claims().setSubject("refresh");
        Date now = new Date();
        Pair<String, Key> key = JwtKey.getRandomKey();
        return Jwts.builder()
                .setClaims(claims) // 정보 저장
                .setIssuedAt(now) // 토큰 발행 시간 정보
                .setExpiration(new Date(now.getTime() + JwtProperties.REFRESH_EXPIRATION_TIME)) // 토큰 만료 시간 설정
                .setHeaderParam(JwsHeader.KEY_ID, key.getFirst()) // kid
                .signWith(key.getSecond()) // signature
                .compact();
    }

    // 토큰의 유효성을 검사하는 메소드
    public static boolean validateToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKeyResolver(SigningKeyResolver.instance).build().parseClaimsJws(token);
            return true;
        } catch (io.jsonwebtoken.security.SecurityException | MalformedJwtException e) {
            log.info("잘못된 JWT 서명입니다.");
        } catch (ExpiredJwtException e) {
            log.info("만료된 JWT 토큰입니다.");
        } catch (UnsupportedJwtException e) {
            log.info("지원되지 않는 JWT 토큰입니다.");
        } catch (IllegalArgumentException e) {
            log.info("JWT 토큰이 잘못되었습니다.");
        }
        return false;
    }
}
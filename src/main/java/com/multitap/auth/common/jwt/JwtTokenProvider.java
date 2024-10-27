package com.multitap.auth.common.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;


@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    @Value("${spring.jwt.access-token-validity}")
    private long accessTokenValidityInMilliseconds;

    @Value("${spring.jwt.refresh-token-validity}")
    private long refreshTokenValidityInMilliseconds;

    @Value("${spring.jwt.secret}")
    private String secret;

    private Key getSignKey() {
        return Keys.hmacShaKeyFor(secret.getBytes()); // 비밀 키를 사용을 통한 키 생성
    }

    public String generateAccessToken() {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + accessTokenValidityInMilliseconds);

        return Jwts.builder()
                .signWith(getSignKey()).issuedAt(now).expiration(expiration)
                .compact();
    }

    public String generateRefreshToken() {
        Date now = new Date();
        Date expiration = new Date(now.getTime() + refreshTokenValidityInMilliseconds);

        return Jwts.builder()
                .signWith(getSignKey()).issuedAt(now).expiration(expiration)
                .compact();
    }

}

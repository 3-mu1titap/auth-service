package com.multitap.auth.common.jwt;

import com.multitap.auth.dto.out.SignInResponseDto;
import com.multitap.auth.entity.AuthUserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


import java.security.Key;
import java.security.SecureRandom;
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
        byte[] keyBytes = new byte[32];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(keyBytes);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public SignInResponseDto generateToken(AuthUserDetail authUserDetail) {
        Date now = new Date();
        Date accessTokenExpiration = new Date(now.getTime() + accessTokenValidityInMilliseconds);
        Date refreshTokenExpiration = new Date(now.getTime() + refreshTokenValidityInMilliseconds);
        Claims claims = Jwts.claims().subject(String.valueOf(authUserDetail.getRole())).build();

        String accessToken = Jwts.builder()
                .signWith(getSignKey()).claims(claims).issuedAt(now).expiration(accessTokenExpiration).compact();

        String refreshToken = Jwts.builder()
                .signWith(getSignKey()).issuedAt(now).expiration(refreshTokenExpiration).compact();

        return SignInResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .uuid(authUserDetail.getUuid())
                .build();

    }

}

package com.multitap.auth.common.jwt;

import com.multitap.auth.dto.out.SignInResponseDto;
import com.multitap.auth.entity.AuthUserDetail;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
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

    // 비밀 키를 생성하는 메서드
    private Key getSignKey() {
        byte[] keyBytes = secret.getBytes();
        // 키 길이를 256비트로 조정
        byte[] paddedKeyBytes = new byte[32];
        System.arraycopy(keyBytes, 0, paddedKeyBytes, 0, Math.min(keyBytes.length, 32));
        return Keys.hmacShaKeyFor(paddedKeyBytes);
    }

    public SignInResponseDto generateToken(AuthUserDetail authUserDetail) {
        Date now = new Date();
        Date accessTokenExpiration = new Date(now.getTime() + accessTokenValidityInMilliseconds);
        Date refreshTokenExpiration = new Date(now.getTime() + refreshTokenValidityInMilliseconds);

        Claims claims = Jwts.claims().setSubject(authUserDetail.getUsername());
        claims.put("role", authUserDetail.getRole());

        String accessToken = Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(accessTokenExpiration)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

        String refreshToken = Jwts.builder()
                .setIssuedAt(now)
                .setExpiration(refreshTokenExpiration)
                .signWith(getSignKey(), SignatureAlgorithm.HS256)
                .compact();

        return SignInResponseDto.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .uuid(authUserDetail.getUuid())
                .build();
    }

    public long getExpiration(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSignKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
        return claims.getExpiration().getTime();
    }
}

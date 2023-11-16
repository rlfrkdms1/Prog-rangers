package com.prograngers.backend.service.auth;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Value;

public class FakeJwtTokenProvider {

    private final Key key;
    private final long validTimeInMillisecond;
    private static final String MEMBER_ID = "memberId";
    private static final String ISSUER = "Prograngers";

    public FakeJwtTokenProvider(@Value("${security-secret-key}") String key,
                                @Value("${security-valid-time-access}") long validTimeInMillisecond) {
        this.key = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        this.validTimeInMillisecond = validTimeInMillisecond;
    }

    public String createAccessToken(Long memberId) {
        Date now = new Date();
        Date validTime = new Date(now.getTime() + validTimeInMillisecond);
        return Jwts.builder()
                .claim(MEMBER_ID, memberId)
                .setExpiration(validTime)
                .setIssuedAt(now)
                .setIssuer(ISSUER)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createAccessToken(String memberId) {
        Date now = new Date();
        Date validTime = new Date(now.getTime() + validTimeInMillisecond);
        return Jwts.builder()
                .claim(MEMBER_ID, memberId)
                .setExpiration(validTime)
                .setIssuedAt(now)
                .setIssuer(ISSUER)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createAccessTokenWithIssuer(Long memberId, String issuer) {
        Date now = new Date();
        Date validTime = new Date(now.getTime() + validTimeInMillisecond);
        return Jwts.builder()
                .claim(MEMBER_ID, memberId)
                .setExpiration(validTime)
                .setIssuedAt(now)
                .setIssuer(issuer)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public String createAccessTokenWithoutIssuer(Long memberId) {
        Date now = new Date();
        Date validTime = new Date(now.getTime() + validTimeInMillisecond);
        return Jwts.builder()
                .claim(MEMBER_ID, memberId)
                .setExpiration(validTime)
                .setIssuedAt(now)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

}

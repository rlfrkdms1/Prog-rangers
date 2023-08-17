package com.prograngers.backend.service.auth;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long validTimeInMillisecond;
    private static final String MEMBER_ID = "memberId";

    @Autowired
    public JwtTokenProvider(@Value("${security-secret-key}") String key,
                            @Value("${security-valid-time-access}") long validTimeInMillisecond) {
        this.key = Keys.hmacShaKeyFor(key.getBytes(StandardCharsets.UTF_8));
        this.validTimeInMillisecond = validTimeInMillisecond;
    }

    public String createAccessToken(Long memberId){
        Date now = new Date();
        Date validTime = new Date(now.getTime() + validTimeInMillisecond);
        return Jwts.builder()
                .claim(MEMBER_ID, memberId)
                .setExpiration(validTime)
                .setIssuedAt(now)
                .signWith(key, SignatureAlgorithm.HS512)
                .compact();
    }

    public Jws<Claims> getClaimsJwt(String token){
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token);
    }

    public Long getMemberId(String accessToken){
        return getClaimsJwt(accessToken).getBody().get(MEMBER_ID, Long.class);
    }

    public Date getExpiredAt(String accessToken){
        return getClaimsJwt(accessToken).getBody().getExpiration();
    }
}

package com.prograngers.backend.service.auth;

import com.prograngers.backend.exception.unauthorization.ExpiredTokenException;
import com.prograngers.backend.exception.unauthorization.FailedSignatureTokenException;
import com.prograngers.backend.exception.unauthorization.IncorrectIssuerTokenException;
import com.prograngers.backend.exception.unauthorization.IncorrectlyConstructedTokenException;
import com.prograngers.backend.exception.unauthorization.InvalidClaimTypeException;
import com.prograngers.backend.exception.unauthorization.MissingIssuerTokenException;
import com.prograngers.backend.exception.unauthorization.UnsupportedTokenException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.IncorrectClaimException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.MissingClaimException;
import io.jsonwebtoken.RequiredTypeException;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.UnsupportedJwtException;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class JwtTokenProvider {

    private final Key key;
    private final long validTimeInMillisecond;
    private static final String MEMBER_ID = "memberId";
    private static final String ISSUER = "Prograngers";

    @Autowired
    public JwtTokenProvider(@Value("${security-secret-key}") String key,
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

    public void validToken(String token) {
        try {
            getClaimsJwt(token);
        } catch (MissingClaimException e) {
            throw new MissingIssuerTokenException();
        } catch (IncorrectClaimException e) {
            throw new IncorrectIssuerTokenException();
        } catch (ExpiredJwtException e) {
            throw new ExpiredTokenException();
        } catch (UnsupportedJwtException e) {
            throw new UnsupportedTokenException();
        } catch (SignatureException e) {
            throw new FailedSignatureTokenException();
        } catch (MalformedJwtException e) {
            throw new IncorrectlyConstructedTokenException();
        }
    }

    public Jws<Claims> getClaimsJwt(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .requireIssuer(ISSUER)
                .build()
                .parseClaimsJws(token);
    }

    public Long getMemberId(String accessToken) {
        try {
            return getClaimsJwt(accessToken).getBody().get(MEMBER_ID, Long.class);
        } catch (RequiredTypeException e) {
            throw new InvalidClaimTypeException();
        }

    }

    public Date getExpiredAt(String accessToken) {
        return getClaimsJwt(accessToken).getBody().getExpiration();
    }
}

package com.prograngers.backend.service.auth;

import static com.prograngers.backend.exception.errorcode.AuthErrorCode.EXPIRED_TOKEN;
import static com.prograngers.backend.exception.errorcode.AuthErrorCode.FAILED_SIGNATURE_TOKEN;
import static com.prograngers.backend.exception.errorcode.AuthErrorCode.INCORRECTLY_CONSTRUCTED_TOKEN;
import static com.prograngers.backend.exception.errorcode.AuthErrorCode.INVALID_CLAIM_TYPE;
import static com.prograngers.backend.exception.errorcode.AuthErrorCode.MISSING_ISSUER_TOKEN;
import static com.prograngers.backend.exception.errorcode.AuthErrorCode.NOT_PROGRANGERS_TOKEN;
import static com.prograngers.backend.exception.errorcode.AuthErrorCode.UNSUPPORTED_TOKEN;

import com.prograngers.backend.exception.UnAuthorizationException;
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
            throw new UnAuthorizationException(MISSING_ISSUER_TOKEN);
        } catch (IncorrectClaimException e) {
            throw new UnAuthorizationException(NOT_PROGRANGERS_TOKEN);
        } catch (ExpiredJwtException e) {
            throw new UnAuthorizationException(EXPIRED_TOKEN);
        } catch (UnsupportedJwtException e) {
            throw new UnAuthorizationException(UNSUPPORTED_TOKEN);
        } catch (SignatureException e) {
            throw new UnAuthorizationException(FAILED_SIGNATURE_TOKEN);
        } catch (MalformedJwtException e) {
            throw new UnAuthorizationException(INCORRECTLY_CONSTRUCTED_TOKEN);
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
            throw new UnAuthorizationException(INVALID_CLAIM_TYPE);
        }

    }

    public Date getExpiredAt(String accessToken) {
        return getClaimsJwt(accessToken).getBody().getExpiration();
    }
}

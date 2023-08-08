package com.prograngers.backend;

import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.index.Indexed;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@RedisHash(value = "refresh_token")
public class RefreshToken {

    @Id
    private Long memberId;

    @Indexed
    private String refreshToken;

    private LocalDateTime expiredAt;

    public RefreshToken(Long memberId, String refreshToken, LocalDateTime expiredAt) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
        this.expiredAt = expiredAt;
    }

}
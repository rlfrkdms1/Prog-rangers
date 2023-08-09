package com.prograngers.backend;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@RedisHash(value = "refresh_token")
public class RefreshToken {

    public static final Long VALID_TIME_REFRESH_TOKEN = 2L * 7 * 24 * 60;

    @Id
    private Long memberId;

    @Indexed
    private String refreshToken;

    @TimeToLive
    private Long expiredAt;

    @Builder
    public RefreshToken(Long memberId, String refreshToken) {
        this.memberId = memberId;
        this.refreshToken = refreshToken;
        this.expiredAt = VALID_TIME_REFRESH_TOKEN;
    }

}
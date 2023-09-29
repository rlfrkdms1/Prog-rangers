package com.prograngers.backend.dto.auth.result;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.service.auth.RefreshToken;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class AuthResult {

    private String accessToken;
    private String refreshToken;
    private Long refreshTokenExpiredAt;
    private String nickname;

    public static AuthResult of(String accessToken, RefreshToken refreshToken, Member member) {
        return AuthResult.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken.getRefreshToken())
                .refreshTokenExpiredAt(refreshToken.getExpiredAt())
                .nickname(member.getNickname())
                .build();
    }

}

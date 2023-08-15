package com.prograngers.backend.service;

import com.prograngers.backend.AuthResult;
import com.prograngers.backend.Encrypt;
import com.prograngers.backend.JwtTokenProvider;
import com.prograngers.backend.dto.LoginRequest;
import com.prograngers.backend.RefreshToken;
import com.prograngers.backend.repository.RefreshTokenRepository;
import com.prograngers.backend.dto.SignUpRequest;
import com.prograngers.backend.entity.Member;
import com.prograngers.backend.exception.InvalidPasswordException;
import com.prograngers.backend.exception.RefreshTokenNotFoundException;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final Encrypt encrypt;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public AuthResult login(LoginRequest loginRequest) {
        //회원 검증
        Member member = findByEmail(loginRequest.getEmail());
        validPassword(member.getPassword(), loginRequest.getPassword());
        //access token 발급
        return issueToken(member.getId());
    }

    private AuthResult issueToken(Long memberId) {
        String accessToken = jwtTokenProvider.createAccessToken(memberId);
        //refresh token 발급, 저장, 쿠키 생성
        RefreshToken refreshToken = RefreshToken.builder()
                .memberId(memberId)
                .refreshToken(UUID.randomUUID().toString())
                .build();
        refreshTokenRepository.save(refreshToken);
        return new AuthResult(accessToken, refreshToken.getRefreshToken(), refreshToken.getExpiredAt());
    }

    @Transactional
    public AuthResult signUp(SignUpRequest signUpRequest) {
        Member member = signUpRequest.toMember();
        member.encodePassword(encrypt.encryptAES256(member.getPassword()));
        memberRepository.save(member);
        //access token 발급
        return issueToken(member.getId());
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }

    public void validPassword(String savedPassword, String inputPassword) {
        if (!encrypt.decryptAES256(savedPassword).equals(inputPassword)) {
            throw new InvalidPasswordException();
        }
    }

    @Transactional
    public AuthResult reissue(String refreshToken) {
        RefreshToken findRefreshToken = validRefreshToken(refreshToken);
        Long memberId = findRefreshToken.getMemberId();
        refreshTokenRepository.delete(findRefreshToken);
        return issueToken(memberId);
    }

    public RefreshToken validRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(RefreshTokenNotFoundException::new);
    }
}

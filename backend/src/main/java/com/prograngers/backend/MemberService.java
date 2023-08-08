package com.prograngers.backend;

import com.prograngers.backend.entity.Member;
import com.prograngers.backend.exception.InvalidPasswordException;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;

    @Transactional
    public AuthResult login(LoginRequest loginRequest) {
        //회원 검증
        Member member = findByEmail(loginRequest.getEmail());
        member.getPassword().matches(loginRequest.getPassword());
        validPassword(loginRequest.getPassword(), member.getPassword());
        //access token 발급
        String accessToken = jwtTokenProvider.createAccessToken(member.getId());
        //refresh token 발급, 저장, 쿠키 생성
        RefreshToken refreshToken = RefreshToken.builder()
                .memberId(member.getId())
                .refreshToken(UUID.randomUUID().toString())
                .build();
        refreshTokenRepository.save(refreshToken);
        return new AuthResult(accessToken, refreshToken.getRefreshToken());

    }

    @Transactional
    public AuthResult signUp(SignUpRequest signUpRequest) {
        Member member = signUpRequest.toMember();
        member.encodePassword(passwordEncoder.encode(member.getPassword()));
        memberRepository.save(member);
        //access token 발급
        String accessToken = jwtTokenProvider.createAccessToken(member.getId());
        //refresh token 발급, 저장, 쿠키 생성
        RefreshToken refreshToken = RefreshToken.builder()
                .memberId(member.getId())
                .refreshToken(UUID.randomUUID().toString())
                .build();
        refreshTokenRepository.save(refreshToken);
        return new AuthResult(accessToken, refreshToken.getRefreshToken());
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }

    public void validPassword(String savedPassword, String inputPassword) {
        if (!passwordEncoder.matches(inputPassword, savedPassword)) {
            throw new InvalidPasswordException();
        }
    }
}

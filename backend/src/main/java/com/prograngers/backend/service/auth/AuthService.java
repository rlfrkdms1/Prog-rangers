package com.prograngers.backend.service.auth;

import com.prograngers.backend.dto.response.auth.google.GoogleTokenResponse;
import com.prograngers.backend.dto.response.auth.google.GoogleUserInfoResponse;
import com.prograngers.backend.dto.response.auth.kakao.KakaoTokenResponse;
import com.prograngers.backend.dto.response.auth.kakao.KakaoUserInfoResponse;
import com.prograngers.backend.dto.response.auth.naver.NaverTokenResponse;
import com.prograngers.backend.dto.response.auth.naver.NaverUserInfoResponse;
import com.prograngers.backend.dto.result.AuthResult;
import com.prograngers.backend.dto.request.auth.LoginRequest;
import com.prograngers.backend.support.Encrypt;
import com.prograngers.backend.exception.unauthorization.AlreadyExistMemberException;
import com.prograngers.backend.exception.unauthorization.AlreadyExistNicknameException;
import com.prograngers.backend.exception.unauthorization.IncorrectCodeInNaverLoginException;
import com.prograngers.backend.repository.RefreshTokenRepository;
import com.prograngers.backend.dto.request.auth.SignUpRequest;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.exception.unauthorization.IncorrectPasswordException;
import com.prograngers.backend.exception.unauthorization.RefreshTokenNotFoundException;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.service.auth.oauth.GoogleOauth;
import com.prograngers.backend.service.auth.oauth.KakaoOauth;
import com.prograngers.backend.service.auth.oauth.NaverOauth;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.UUID;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class AuthService {

    private final MemberRepository memberRepository;
    private final JwtTokenProvider jwtTokenProvider;
    private final RefreshTokenRepository refreshTokenRepository;
    private final KakaoOauth kakaoOauth;
    private final GoogleOauth googleOauth;
    private final NaverOauth naverOauth;
    private final NicknameGenerator nicknameGenerator;

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
        RefreshToken refreshToken = refreshTokenRepository.save(RefreshToken.builder().memberId(memberId).refreshToken(UUID.randomUUID().toString()).build());
        return new AuthResult(accessToken, refreshToken.getRefreshToken(), refreshToken.getExpiredAt());
    }

    @Transactional
    public AuthResult signUp(SignUpRequest signUpRequest) {
        validExistMember(signUpRequest);
        validExistNickname(signUpRequest.getNickname());
        Member member = signUpRequest.toMember();
        member.encodePassword(member.getPassword());
        memberRepository.save(member);
        //access token 발급
        return issueToken(member.getId());
    }

    private void validExistNickname(String nickname) {
        if(memberRepository.findByNickname(nickname).isPresent()){
            throw new AlreadyExistNicknameException();
        }
    }

    private void validExistMember(SignUpRequest signUpRequest) {
        if(memberRepository.findByEmail(signUpRequest.getEmail()).isPresent()){
            throw new AlreadyExistMemberException();
        }
    }

    public Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }

    public void validPassword(String savedPassword, String inputPassword) {
        if (!Encrypt.decoding(savedPassword).equals(inputPassword)) {
            throw new IncorrectPasswordException();
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

    @Transactional
    public AuthResult kakaoLogin(String code) {
        KakaoTokenResponse kakaoTokenResponse = kakaoOauth.kakaoGetToken(code);
        KakaoUserInfoResponse kakaoUserInfoResponse = kakaoOauth.kakaoGetUserInfo(kakaoTokenResponse.getAccess_token());
        Member member = memberRepository.findBySocialId(kakaoUserInfoResponse.getId())
                .orElseGet(() -> socialRegister(kakaoUserInfoResponse.toMember()));
        return issueToken(member.getId());
    }
    @Transactional
    public AuthResult googleLogin(String code) {
        GoogleTokenResponse googleTokenResponse = googleOauth.googleGetToken(code);
        GoogleUserInfoResponse googleUserInfoResponse = googleOauth.googleGetUserInfo(googleTokenResponse.getAccess_token());
        Member member = memberRepository.findBySocialId(Long.valueOf(googleUserInfoResponse.getId().hashCode()))
                .orElseGet(() -> socialRegister(googleUserInfoResponse.toMember()));
        return issueToken(member.getId());
    }

    @Transactional
    public AuthResult naverLogin(String code, String state) {
        validCode(code);
        NaverTokenResponse naverToken = naverOauth.getNaverToken(code, state);
        NaverUserInfoResponse userInfo = naverOauth.getUserInfo(naverToken.getAccess_token());
        Member member = memberRepository.findBySocialId(Long.valueOf(userInfo.getResponse().getId().hashCode()))
                .orElseGet(() -> socialRegister(userInfo.toMember()));
        return issueToken(member.getId());
    }

    private void validCode(String code) {
        if(!code.equals(naverOauth.getCode())) throw new IncorrectCodeInNaverLoginException();
    }

    private Member socialRegister(Member member) {
        String nickname = "";
        do {
            nickname = nicknameGenerator.getRandomNickname().getNickname();
        } while (isDuplicateNickname(nickname));
        member.createRandomNickname(nickname);
        return memberRepository.save(member);
    }

    private boolean isDuplicateNickname(String nickname) {
        return memberRepository.findByNickname(nickname).isPresent();
    }

    public void checkNicknameDuplication(String nickname) {
        if (isDuplicateNickname(nickname)) {
            throw new AlreadyExistNicknameException();
        }
    }
}

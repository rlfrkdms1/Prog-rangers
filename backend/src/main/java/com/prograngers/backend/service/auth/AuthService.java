package com.prograngers.backend.service.auth;

import com.prograngers.backend.dto.auth.response.google.GetGoogleTokenResponse;
import com.prograngers.backend.dto.auth.response.google.GetGoogleUserInfoResponse;
import com.prograngers.backend.dto.auth.response.kakao.GetKakaoTokenResponse;
import com.prograngers.backend.dto.auth.response.kakao.GetKakaoUserInfoResponse;
import com.prograngers.backend.dto.auth.response.naver.GetNaverTokenResponse;
import com.prograngers.backend.dto.auth.response.naver.GetNaverUserInfoResponse;
import com.prograngers.backend.dto.auth.result.AuthResult;
import com.prograngers.backend.dto.auth.request.LoginRequest;
import com.prograngers.backend.exception.unauthorization.QuitMemberException;
import com.prograngers.backend.support.Encrypt;
import com.prograngers.backend.exception.unauthorization.AlreadyExistMemberException;
import com.prograngers.backend.exception.unauthorization.AlreadyExistNicknameException;
import com.prograngers.backend.repository.RefreshTokenRepository;
import com.prograngers.backend.dto.auth.request.SignUpRequest;
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
        Member member = findByEmail(loginRequest.getEmail());
        validQuit(member);
        validPassword(member.getPassword(), loginRequest.getPassword());
        return issueToken(member);
    }

    private void validQuit(Member member) {
        if (!member.isUsable()) {
            throw new QuitMemberException();
        }
    }

    private Member findByEmail(String email) {
        return memberRepository.findByEmail(email).orElseThrow(MemberNotFoundException::new);
    }

    private void validPassword(String savedPassword, String inputPassword) {
        if (!savedPassword.equals(Encrypt.encoding(inputPassword))) {
            throw new IncorrectPasswordException();
        }
    }

    private AuthResult issueToken(Member member) {
        Long memberId = member.getId();
        String accessToken = jwtTokenProvider.createAccessToken(memberId);
        RefreshToken refreshToken = refreshTokenRepository.save(createRefreshToken(memberId));
        return AuthResult.of(accessToken, refreshToken, member);
    }

    private RefreshToken createRefreshToken(Long memberId) {
        return RefreshToken.builder().memberId(memberId).refreshToken(UUID.randomUUID().toString()).build();
    }

    @Transactional
    public AuthResult signUp(SignUpRequest signUpRequest) {
        validAlreadyExistEmail(signUpRequest.getEmail());
        validAlreadyExistNickname(signUpRequest.getNickname());
        Member member = signUpRequest.toMember();
        member.encodePassword(member.getPassword());
        memberRepository.save(member);
        return issueToken(member);
    }

    private void validAlreadyExistEmail(String email) {
        if(memberRepository.existsByEmail(email)){
            throw new AlreadyExistMemberException();
        }
    }


    @Transactional
    public AuthResult reissue(String refreshToken) {
        RefreshToken findRefreshToken = validRefreshToken(refreshToken);
        Long memberId = findRefreshToken.getMemberId();
        refreshTokenRepository.delete(findRefreshToken);
        return issueToken(findMemberById(memberId));
    }

    public Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    private RefreshToken validRefreshToken(String refreshToken) {
        return refreshTokenRepository.findByRefreshToken(refreshToken)
                .orElseThrow(RefreshTokenNotFoundException::new);
    }

    @Transactional
    public AuthResult kakaoLogin(String code) {
        GetKakaoTokenResponse getKakaoTokenResponse = kakaoOauth.kakaoGetToken(code);
        GetKakaoUserInfoResponse getKakaoUserInfoResponse = kakaoOauth.kakaoGetUserInfo(getKakaoTokenResponse.getAccess_token());
        Member member = memberRepository.findBySocialId(getKakaoUserInfoResponse.getId())
                .orElseGet(() -> socialRegister(getKakaoUserInfoResponse.toMember()));
        return issueToken(member);
    }
    @Transactional
    public AuthResult googleLogin(String code) {
        GetGoogleTokenResponse getGoogleTokenResponse = googleOauth.googleGetToken(code);
        GetGoogleUserInfoResponse getGoogleUserInfoResponse = googleOauth.googleGetUserInfo(getGoogleTokenResponse.getAccess_token());
        Member member = memberRepository.findBySocialId(Long.valueOf(getGoogleUserInfoResponse.getId().hashCode()))
                .orElseGet(() -> socialRegister(getGoogleUserInfoResponse.toMember()));
        return issueToken(member);
    }

    @Transactional
    public AuthResult naverLogin(String code, String state) {
        GetNaverTokenResponse naverToken = naverOauth.getNaverToken(code, state);
        log.info(naverToken.toString());
        GetNaverUserInfoResponse userInfo = naverOauth.getUserInfo(naverToken.getAccess_token());
        Member member = memberRepository.findBySocialId(Long.valueOf(userInfo.getResponse().getId().hashCode()))
                .orElseGet(() -> socialRegister(userInfo.toMember()));
        return issueToken(member);
    }

    private Member socialRegister(Member member) {
        String nickname = "";
        do {
            nickname = nicknameGenerator.getRandomNickname().getNickname();
        } while (alreadyExistNickname(nickname));
        member.updateRandomNickname(nickname);
        return memberRepository.save(member);
    }

    private boolean alreadyExistNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    public void validAlreadyExistNickname(String nickname) {
        if (alreadyExistNickname(nickname)) {
            throw new AlreadyExistNicknameException();
        }
    }
}

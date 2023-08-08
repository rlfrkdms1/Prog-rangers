package com.prograngers.backend;

import com.prograngers.backend.entity.Member;
import com.prograngers.backend.exception.InvalidPasswordException;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public void login(LoginRequest loginRequest) {
        //회원 검증
        Member member = findByEmail(loginRequest.getEmail());
        member.getPassword().matches(loginRequest.getPassword());
        validPassword(loginRequest.getPassword(), member.getPassword());
        //access token 발급
        //refresh token 발급, 저장, 쿠키 생성

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

package com.prograngers.backend.dto.request.auth;

import com.prograngers.backend.entity.member.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SignUpRequest {


    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @Email(message = "이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickname;


    public Member toMember(){
        return Member.builder()
                .email(email)
                .password(password)
                .nickname(nickname)
                .build();
    }


}

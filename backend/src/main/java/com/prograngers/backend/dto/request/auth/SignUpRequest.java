package com.prograngers.backend.dto.request.auth;

import com.prograngers.backend.entity.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;

@Getter
public class SignUpRequest {

    @NotBlank(message = "이름은 필수입니다.")
    private String username;

    @NotBlank(message = "비밀번호는 필수입니다.")
    private String password;

    @Email(message = "이메일 형식이 아닙니다.")
    @NotBlank(message = "이메일은 필수입니다.")
    private String email;

    @NotBlank(message = "닉네임은 필수입니다.")
    private String nickName;

    @NotBlank(message = "전화번호는 필수입니다.")
    @Size(min = 11, max = 11, message = "전화번호는 11자리 입니다.")
    private String phoneNumber;

    public Member toMember(){
        return Member.builder()
                .name(username)
                .email(email)
                .password(password)
                .phoneNumber(phoneNumber)
                .nickname(nickName)
                .build();
    }
}

package com.prograngers.backend.dto.auth.request;

import com.prograngers.backend.entity.member.Member;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import static com.prograngers.backend.entity.member.MemberType.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
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
<<<<<<< HEAD:backend/src/main/java/com/prograngers/backend/dto/request/auth/SignUpRequest.java
=======
                .type(BASIC)
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1:backend/src/main/java/com/prograngers/backend/dto/auth/request/SignUpRequest.java
                .build();
    }
}

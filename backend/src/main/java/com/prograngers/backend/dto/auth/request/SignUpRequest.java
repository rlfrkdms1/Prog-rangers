package com.prograngers.backend.dto.auth.request;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.MemberType;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
<<<<<<< HEAD:backend/src/main/java/com/prograngers/backend/dto/request/auth/SignUpRequest.java
import static com.prograngers.backend.entity.member.MemberType.*;

@Getter
@AllArgsConstructor
@NoArgsConstructor
=======

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
>>>>>>> fceb98ba6fcbfc31fd0f4929dcfe0b6eb9a027e5:backend/src/main/java/com/prograngers/backend/dto/auth/request/SignUpRequest.java
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
                .type(BASIC)
=======
                .type(MemberType.BASIC)
>>>>>>> fceb98ba6fcbfc31fd0f4929dcfe0b6eb9a027e5:backend/src/main/java/com/prograngers/backend/dto/auth/request/SignUpRequest.java
                .build();
    }


}

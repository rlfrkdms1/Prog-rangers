package com.prograngers.backend.dto.member.response;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.MemberType;
import com.prograngers.backend.support.Encrypt;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class MemberAccountInfoResponse {

    private MemberType type;

    private String nickname;

    private String email;

    private String github;

    private String introduction;

    private LocalDateTime passwordModifiedAt;

    private String password;

    private String photo;

    public static MemberAccountInfoResponse from(Member member) {
        return MemberAccountInfoResponse.builder()
                .type(member.getType())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .github(member.getGithub())
                .introduction(member.getIntroduction())
                .passwordModifiedAt(member.getPasswordModifiedAt())
                .password(Encrypt.decoding(member.getPassword()))
                .photo(member.getPhoto())
                .build();
    }

}

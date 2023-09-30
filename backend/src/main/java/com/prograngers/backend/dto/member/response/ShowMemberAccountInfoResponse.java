package com.prograngers.backend.dto.member.response;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.MemberType;
import lombok.Builder;
import lombok.Data;
import java.time.LocalDateTime;

@Data
@Builder
public class ShowMemberAccountInfoResponse {

    private MemberType type;

    private String nickname;

    private String email;

    private String github;

    private String introduction;

    private LocalDateTime passwordModifiedAt;

    private String photo;

    public static ShowMemberAccountInfoResponse from(Member member) {
        return ShowMemberAccountInfoResponse.builder()
                .type(member.getType())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .github(member.getGithub())
                .introduction(member.getIntroduction())
<<<<<<< HEAD:backend/src/main/java/com/prograngers/backend/dto/response/member/MemberAccountInfoResponse.java
                .currentlyModifiedAt(member.getCurrentlyModifiedAt())
=======
                .passwordModifiedAt(member.getPasswordModifiedAt())
                .password(Encrypt.decoding(member.getPassword()))
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1:backend/src/main/java/com/prograngers/backend/dto/member/response/ShowMemberAccountInfoResponse.java
                .photo(member.getPhoto())
                .build();
    }

}

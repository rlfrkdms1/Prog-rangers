package com.prograngers.backend.dto.member.response;

import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberProfileResponse {
    /**
     * 프로필 사진, 닉네임, 자기소개, 팔로우, 팔로잉, 깃허브
     */
    private String photo;

    private String nickname;

    private String introduction;

    private Integer follow;
    private Integer following;

    private String github;

    /**
     * 뱃지
     */

    private String badge;

    /**
     *  문제제목, 풀이알고리즘, 풀이 자료구조, 저지명
     *  풀이설명, 소스코드
     */

    List<MemberProfileProblemSolution> list;

}

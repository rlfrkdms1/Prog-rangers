package com.prograngers.backend.dto.member.response;

import com.prograngers.backend.entity.Badge;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.BadgeConstant;
import com.prograngers.backend.entity.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
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

    private Long follow;
    private Long following;

    private String github;

    /**
     * 뱃지
     */

    private List<BadgeConstant> badge;

    /**
     *  문제제목, 풀이알고리즘, 풀이 자료구조, 저지명
     *  풀이설명, 소스코드
     */

    List<MemberProfileProblemSolution> list;

    public static MemberProfileResponse from(Member member, List<Badge> badges, List<Solution> solutions, Long follow, Long following) {
        List<BadgeConstant> badgeList = new ArrayList<>();
        badges.stream().map(badge->badge.getBadgeType())
                .forEach(badge->badgeList.add(badge));
        List<MemberProfileProblemSolution> problemSolutionList = new ArrayList<>();
        solutions.stream()
                .forEach(solution->{
                    problemSolutionList.add(
                            MemberProfileProblemSolution.builder()
                                    .problemName(solution.getProblem().getTitle())
                                    .dataStructure(solution.getDataStructure())
                                    .algorithm(solution.getAlgorithm())
                                    .ojName(solution.getProblem().getOjName())
                                    .description(solution.getDescription())
                                    .code(solution.getCode())
                                    .build()
                    );
                });

        return MemberProfileResponse.builder()
                .photo(member.getPhoto())
                .nickname(member.getNickname())
                .introduction(member.getIntroduction())
                .follow(follow)
                .following(following)
                .badge(badgeList)
                .list(problemSolutionList)
                .build();
    }
}

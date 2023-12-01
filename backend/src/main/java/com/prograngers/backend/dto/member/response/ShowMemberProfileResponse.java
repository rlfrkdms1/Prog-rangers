package com.prograngers.backend.dto.member.response;

import com.prograngers.backend.entity.badge.Badge;
import com.prograngers.backend.entity.badge.BadgeConstant;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

@Getter
@Builder
@Slf4j
public class ShowMemberProfileResponse {
    private static final Long LAST_PAGE_CURSOR = -1L;
    private static int SIZE_PER_SCROLL = 3;
    private String photo;
    private String nickname;
    private String introduction;
    private Long follow;
    private Long following;
    private String github;
    private List<BadgeConstant> badge;
    private List<SolutionWithProblemResponse> list;
    private Long cursor;

    public static ShowMemberProfileResponse from(Member member, List<Badge> badges, List<Solution> profileSolutions,
                                                 Long follow, Long following, Long cursor) {

        return ShowMemberProfileResponse.builder()
                .photo(member.getPhoto())
                .nickname(member.getNickname())
                .introduction(member.getIntroduction())
                .follow(follow)
                .following(following)
                .badge(getBadgeList(badges))
                .list(getProblemSolutionList(profileSolutions))
                .cursor(cursor)
                .build();
    }

    private static List<SolutionWithProblemResponse> getProblemSolutionList(List<Solution> solutions) {
        return solutions.stream()
                .map(solution -> SolutionWithProblemResponse.from(solution)).toList();
    }

    private static List<BadgeConstant> getBadgeList(List<Badge> badges) {
        return badges.stream().map(badge -> badge.getBadgeType()).toList();
    }
}

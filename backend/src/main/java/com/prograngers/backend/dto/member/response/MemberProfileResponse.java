package com.prograngers.backend.dto.member.response;

import com.prograngers.backend.entity.badge.Badge;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.entity.badge.BadgeConstant;
import com.prograngers.backend.entity.member.Member;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class MemberProfileResponse {
    private static int SIZE_PER_SCROLL = 3;
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
    /**
     *  무한스크롤 위한 값들
     */
    // 다음에 pathvariable을 뭘로 요청해야 하는지 알려주기 위한 값, 커서가 -1L이면 마지막
    private Long cursor;

    public static MemberProfileResponse from(Member member, List<Badge> badges, List<Solution> solutions, Long follow, Long following) {

        // 무한스크롤 isLast, 커서
        Long cursor = -1L;
        if (!isLastScroll(solutions)) cursor = getCursor(solutions);

        return MemberProfileResponse.builder()
                .photo(member.getPhoto())
                .nickname(member.getNickname())
                .introduction(member.getIntroduction())
                .follow(follow)
                .following(following)
                .badge(getBadgeList(badges))
                .list(getProblemSolutionList(solutions))
                .cursor(cursor)
                .build();
    }

    private static List<MemberProfileProblemSolution> getProblemSolutionList(List<Solution> solutions) {
        return solutions.stream()
                .map(solution -> MemberProfileProblemSolution.from(solution)).toList();
    }

    private static List<BadgeConstant> getBadgeList(List<Badge> badges) {
        return badges.stream().map(badge -> badge.getBadgeType()).toList();
    }

    private static boolean isLastScroll(List<Solution> solutions) {
        return solutions.size() < SIZE_PER_SCROLL;
    }

    private static Long getCursor(List<Solution> solutions) {
        Long cursor;
        cursor = solutions.get(SIZE_PER_SCROLL-1).getId();
        solutions.remove(SIZE_PER_SCROLL-1);
        return cursor;
    }
}

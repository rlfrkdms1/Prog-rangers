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
    // 다음에 pathvariable을 뭘로 요청해야 하는지 알려주기 위한 값
    private Long cursor;

    // 마지막 페이지인지 알려주기 위한 값
    private Boolean isLast;

    public static MemberProfileResponse from(Member member, List<Badge> badges, List<Solution> solutions, Long follow, Long following) {

        // 무한스크롤 isLast, 커서
        boolean isLast = false;
        Long cursor = -1L;
        if (solutions.size()<3){
            isLast = true;
        } else {
            cursor = solutions.get(2).getId();
            solutions.remove(2);
        }

        List<BadgeConstant> badgeList = badges.stream().map(badge -> badge.getBadgeType()).toList();
        List<MemberProfileProblemSolution> problemSolutionList = solutions.stream()
                .map(solution -> MemberProfileProblemSolution.from(solution)).toList();

        return MemberProfileResponse.builder()
                .photo(member.getPhoto())
                .nickname(member.getNickname())
                .introduction(member.getIntroduction())
                .follow(follow)
                .following(following)
                .badge(badgeList)
                .list(problemSolutionList)
                .cursor(cursor)
                .isLast(isLast)
                .build();
    }
}

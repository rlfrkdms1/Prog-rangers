package com.prograngers.backend.repository.member;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import java.util.List;

public interface MemberCustomRepository {
    List<Member> getLimitRecommendedMembers(Problem problem, Long limit, Long memberId);

    List<Member> getOtherMembersOrderByFollow(Long memberId);
}

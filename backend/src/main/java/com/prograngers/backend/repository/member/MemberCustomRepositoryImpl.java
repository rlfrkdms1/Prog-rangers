package com.prograngers.backend.repository.member;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.QMember;
import com.prograngers.backend.entity.problem.Problem;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.prograngers.backend.entity.QFollow.*;
import static com.prograngers.backend.entity.member.QMember.*;
import static com.prograngers.backend.entity.solution.QSolution.*;

@Repository
@RequiredArgsConstructor
public class MemberCustomRepositoryImpl implements MemberCustomRepository{

    private final JPAQueryFactory jpaQueryFactory;

    public List<Member> getLimitRecommendedMembers(Problem problem,Long limit){
        return jpaQueryFactory.select(member)
                .from(member)
                .join(solution)
                .on(solution.member.eq(member))
                .join(follow)
                .on(follow.followingId.eq(member.id))
                .groupBy(member)
                .orderBy(follow.count().desc())
                .limit(limit)
                .fetch();
    }
}

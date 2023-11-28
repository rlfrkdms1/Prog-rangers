package com.prograngers.backend.repository.member;

import static com.prograngers.backend.entity.QFollow.follow;
import static com.prograngers.backend.entity.member.QMember.member;
import static com.prograngers.backend.entity.solution.QSolution.solution;

import com.prograngers.backend.entity.QFollow;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.querydsl.jpa.JPAExpressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
@Slf4j
public class MemberCustomRepositoryImpl implements MemberCustomRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<Member> getLimitRecommendedMembers(Problem problem, Long limit, Long memberId) {
        QFollow subFollow = new QFollow("subFollow");
        if (problem != null) {
            return jpaQueryFactory.select(member)
                    .from(member)
                    .join(solution)
                    .on(solution.member.eq(member))
                    .leftJoin(follow)
                    .on(follow.followingId.eq(member.id))
                    .where(member.id.ne(memberId), solution.problem.eq(problem))
                    .where(member.id.notIn(
                            JPAExpressions.select(subFollow.followingId)
                                    .from(subFollow)
                                    .where(subFollow.followerId.eq(memberId))
                    ))
                    .groupBy(member)
                    .orderBy(follow.count().desc())
                    .limit(limit)
                    .fetch();
        }
        //내가 최근에 푼 문제가 없으면 그냥 팔로워가 많은 순서대로 추천한다
        return jpaQueryFactory.select(member)
                .from(member)
                .leftJoin(follow)
                .on(follow.followingId.eq(member.id))
                .where(member.id.ne(memberId))
                .where(member.id.notIn(
                        JPAExpressions.select(subFollow.followingId)
                                .from(subFollow)
                                .where(subFollow.followerId.eq(memberId))
                ))
                .groupBy(member)
                .orderBy(follow.count().desc())
                .limit(limit)
                .fetch();
    }

    public List<Member> getOtherMembersOrderByFollow(Long memberId) {
        QFollow subFollow = new QFollow("subFollow");
        return jpaQueryFactory.select(member)
                .from(member)
                .leftJoin(follow)
                .on(follow.followingId.eq(member.id))
                .where(member.id.ne(memberId))
                .where(member.id.notIn(
                        JPAExpressions.select(subFollow.followingId)
                                .from(subFollow)
                                .where(subFollow.followerId.eq(memberId))
                ))
                .groupBy(member)
                .orderBy(follow.count().desc())
                .fetch();
    }
}

package com.prograngers.backend.repository.member;


import com.prograngers.backend.entity.Follow;
import com.prograngers.backend.entity.Likes;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.repository.follow.FollowRepository;
import com.prograngers.backend.repository.likes.LikesRepository;
import com.prograngers.backend.repository.problem.ProblemRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import com.prograngers.backend.support.RepositoryTest;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.List;

import static com.prograngers.backend.entity.solution.LanguageConstant.JAVA;
import static com.prograngers.backend.support.fixture.MemberFixture.길가은;
import static com.prograngers.backend.support.fixture.MemberFixture.장지담;
import static com.prograngers.backend.support.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.support.fixture.SolutionFixture.공개_풀이;

@RepositoryTest
class MemberRepositoryTest {
    @Autowired
    private SolutionRepository solutionRepository;
    @Autowired
    private ProblemRepository problemRepository;
    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private FollowRepository followRepository;

    @Test
    @DisplayName("문제가 주어졌을 때 해당 문제를 푼 회원들을 팔로우가 많은 순으로 정렬해서 가져올 수 있다. 이 때 내가 팔로우하지 않은 회원들을 가져온다")
    void getLimitRecommendedMembersTest(){

        //given
        Member member1 = 저장(장지담.기본_정보_생성());
        Member member2 = 저장(길가은.기본_정보_생성());
        Member member3 = 저장(길가은.기본_정보_생성());
        Member member4 = 저장(길가은.기본_정보_생성());
        Member member5 = 저장(길가은.기본_정보_생성());
        Member member6 = 저장(길가은.기본_정보_생성());

        /**
         *  member1: 나
         *  member2: 나랑 같은 문제를 풀었지만 내가 이미 팔로우함(결과에 포함 X)
         *  member3(1명), member4(2명): 나랑 같은 문제를 풀었고 내가 팔로우하지 않음, 다른 팔로워는 있음
         *  member5 : 나랑 같은 문제를 풀었지만 팔로워가 없음(결과의 마지막에 포함되어야함)
         *  member6: 팔로워는 제일 많지만 나랑 같은 문제를 풀지 않음
         */
        저장(팔로우_생성(member1.getId(),member2.getId()));
        저장(팔로우_생성(member1.getId(),member3.getId()));
        저장(팔로우_생성(member2.getId(),member1.getId()));

        저장(팔로우_생성(member4.getId(),member2.getId()));
        저장(팔로우_생성(member4.getId(),member3.getId()));

        저장(팔로우_생성(member3.getId(),member5.getId()));

        저장(팔로우_생성(member6.getId(),member2.getId()));
        저장(팔로우_생성(member6.getId(),member3.getId()));
        저장(팔로우_생성(member6.getId(),member4.getId()));
        저장(팔로우_생성(member6.getId(),member5.getId()));

        Problem problem1 = 저장(백준_문제.기본_정보_생성());
        Problem problem2 = 저장(백준_문제.기본_정보_생성());

        저장(공개_풀이.기본_정보_생성(problem1, member1, LocalDateTime.now(), JAVA, 3));
        저장(공개_풀이.기본_정보_생성(problem1, member2, LocalDateTime.now(), JAVA, 3));
        저장(공개_풀이.기본_정보_생성(problem1, member3, LocalDateTime.now(), JAVA, 3));
        저장(공개_풀이.기본_정보_생성(problem1, member4, LocalDateTime.now(), JAVA, 3));
        저장(공개_풀이.기본_정보_생성(problem1, member5, LocalDateTime.now(), JAVA, 3));
        저장(공개_풀이.기본_정보_생성(problem2, member6, LocalDateTime.now(), JAVA, 3));

        //when
        List<Member> result = memberRepository.getLimitRecommendedMembers(problem1, 3L, member1.getId());

        //then
        Assertions.assertThat(result).containsExactly(member4,member3,member5);
    }

    private Follow 팔로우_생성(Long followingId, Long followerId){ return Follow.builder().followingId(followingId).followerId(followerId).build(); }
    private Member 저장(Member member) {
        return memberRepository.save(member);
    }

    private Problem 저장(Problem problem) {
        return problemRepository.save(problem);
    }

    private Solution 저장(Solution solution) {
        return solutionRepository.save(solution);
    }

    private Follow 저장(Follow follow) {
        return followRepository.save(follow);
    }
}
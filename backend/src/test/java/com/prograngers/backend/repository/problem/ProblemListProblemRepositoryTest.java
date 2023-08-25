package com.prograngers.backend.repository.problem;

import com.prograngers.backend.TestConfig;
import com.prograngers.backend.entity.constants.SortConstant;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.data.domain.PageRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.prograngers.backend.entity.constants.AlgorithmConstant.*;
import static com.prograngers.backend.entity.constants.DataStructureConstant.*;
import static com.prograngers.backend.entity.constants.LanguageConstant.JAVA;
import static com.prograngers.backend.entity.constants.SortConstant.*;
import static com.prograngers.backend.fixture.MemberFixture.길가은1;
import static com.prograngers.backend.fixture.ProblemFixture.문제1;
import static com.prograngers.backend.fixture.ProblemFixture.문제2;
import static com.prograngers.backend.fixture.ProblemFixture.문제3;
import static com.prograngers.backend.fixture.SolutionFixture.풀이1;
import static com.prograngers.backend.fixture.SolutionFixture.풀이2;
import static com.prograngers.backend.fixture.SolutionFixture.풀이3;
import static com.prograngers.backend.fixture.SolutionFixture.풀이4;
import static com.prograngers.backend.fixture.SolutionFixture.풀이5;
import static com.prograngers.backend.fixture.SolutionFixture.풀이6;
import static com.prograngers.backend.fixture.SolutionFixture.풀이7;
import static com.prograngers.backend.fixture.SolutionFixture.풀이8;
import static com.prograngers.backend.fixture.SolutionFixture.풀이9;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Slf4j
@Transactional
@Import(TestConfig.class)
class ProblemListProblemRepositoryTest {
    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    SolutionRepository solutionRepository;

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("문제 목록 조회시 가장 최근 풀이의 날짜에 따라 최신순으로 정렬된다")
    @Test
    void 문제_목록_조회_날짜_최신순() {
        // given
        // 회원
        Member member = 멤버_저장(길가은1.아이디_값_지정_멤버_생성(null));

        // 문제
        Problem problem1 = 문제_저장(문제1.아이디_값_지정_문제_생성(null));
        Problem problem2 = 문제_저장(문제2.아이디_값_지정_문제_생성(null));
        Problem problem3 = 문제_저장(문제3.아이디_값_지정_문제_생성(null));

        // 풀이  풀이 9 ~ 1 순으로 최신
        Solution solution1 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE, JAVA));
        Solution solution2 = 풀이_저장(풀이7.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE, JAVA));
        Solution solution3 = 풀이_저장(풀이8.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE, JAVA));
        Solution solution4 = 풀이_저장(풀이4.언어_포함_솔루션_생성(null, problem2, member, BFS, QUEUE, JAVA));
        Solution solution5 = 풀이_저장(풀이5.언어_포함_솔루션_생성(null, problem2, member,  BFS, QUEUE, JAVA));
        Solution solution6 = 풀이_저장(풀이6.언어_포함_솔루션_생성(null, problem2, member,  BFS, QUEUE, JAVA));
        Solution solution7 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem3, member,  BFS, QUEUE, JAVA));
        Solution solution8 = 풀이_저장(풀이2.언어_포함_솔루션_생성(null, problem3, member,  BFS, QUEUE, JAVA));
        Solution solution9 = 풀이_저장(풀이9.언어_포함_솔루션_생성(null, problem3, member, BFS, QUEUE, JAVA));

        // when
        List<Problem> result = problemRepository.findAll(
                PageRequest.of(0,4), null, null, NEWEST
        ).getContent();

        // then // 현재 id에 의해 정렬함
        Assertions.assertThat(result.get(0).getTitle()).isEqualTo("문제제목3");
        Assertions.assertThat(result.get(1).getTitle()).isEqualTo("문제제목1");
        Assertions.assertThat(result.get(2).getTitle()).isEqualTo("문제제목2");
    }

    @DisplayName("문제 목록 조회 시 풀이의 알고리즘, 자료구조 필터에 따라 조회한다")
    @Test
    void 문제_목록_필터() {
        // given
        // 회원
        Member member = 멤버_저장(길가은1.아이디_값_지정_멤버_생성(null));

        // 문제
        Problem problem1 = 문제_저장(문제1.아이디_값_지정_문제_생성(null));
        Problem problem2 = 문제_저장(문제1.아이디_값_지정_문제_생성(null));
        Problem problem3 = 문제_저장(문제1.아이디_값_지정_문제_생성(null));
        Problem problem4 = 문제_저장(문제1.아이디_값_지정_문제_생성(null));

        // 풀이
        Solution solution1 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE,JAVA));
        Solution solution2 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem2, member,  DFS, QUEUE,JAVA));
        Solution solution3 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem3, member,  BFS, ARRAY,JAVA));
        Solution solution4 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem4, member,  DFS, ARRAY,JAVA));

        // when
        List<Problem> result1 = problemRepository.findAll(
                PageRequest.of(0,4), null, BFS, NEWEST
        ).getContent();
        List<Problem> result2 = problemRepository.findAll(
                PageRequest.of(0,4),null, DFS, NEWEST
        ).getContent();
        List<Problem> result3 = problemRepository.findAll(
                PageRequest.of(0,4),QUEUE, BFS, NEWEST
        ).getContent();

        // then
        Assertions.assertThat(result1).contains(problem1, problem3);
        Assertions.assertThat(result1).doesNotContain(problem2, problem4);
        Assertions.assertThat(result2).contains(problem2, problem4);
        Assertions.assertThat(result2).doesNotContain(problem1, problem3);
        Assertions.assertThat(result3).contains(problem1);
        Assertions.assertThat(result3).doesNotContain(problem2, problem3, problem4);
    }

    @DisplayName("문제 목록 조회 시 페이지에 맞는 문제를 가져온다")
    @Test
    void 문제_목록_조회_페이징() {
        // given
        // 회원
        Member member = 멤버_저장(길가은1.아이디_값_지정_멤버_생성(null));

        // 문제 : 문제3 ~ 문제1 순서로 최신
        Problem problem1 = 문제_저장(문제1.아이디_값_지정_문제_생성(null));
        Problem problem2 = 문제_저장(문제2.아이디_값_지정_문제_생성(null));
        Problem problem3 = 문제_저장(문제2.아이디_값_지정_문제_생성(null));
        Problem problem4 = 문제_저장(문제2.아이디_값_지정_문제_생성(null));
        Problem problem5 = 문제_저장(문제2.아이디_값_지정_문제_생성(null));
        Problem problem6 = 문제_저장(문제3.아이디_값_지정_문제_생성(null));
        Problem problem7 = 문제_저장(문제3.아이디_값_지정_문제_생성(null));
        Problem problem8 = 문제_저장(문제3.아이디_값_지정_문제_생성(null));
        Problem problem9 = 문제_저장(문제3.아이디_값_지정_문제_생성(null));

        //  풀이 : 문제를 풀이랑 조인해서 가져오기 때문에 풀이도 필요하다
        Solution solution1 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE, JAVA));
        Solution solution2 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem2, member,  BFS, QUEUE, JAVA));
        Solution solution3 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem3, member,  BFS, QUEUE, JAVA));
        Solution solution4 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem4, member,  BFS, QUEUE, JAVA));
        Solution solution5 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem5, member,  BFS, QUEUE, JAVA));
        Solution solution6 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem6, member,  BFS, QUEUE, JAVA));
        Solution solution7 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem7, member,  BFS, QUEUE, JAVA));
        Solution solution8 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem8, member,  BFS, QUEUE, JAVA));
        Solution solution9 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem9, member,  BFS, QUEUE, JAVA));


        // when
        List<Problem> result1 = problemRepository.findAll(PageRequest.of(0,4),null, null, NEWEST).getContent();
        List<Problem> result2 = problemRepository.findAll(PageRequest.of(1,4), null, null, NEWEST).getContent();
        List<Problem> result3 = problemRepository.findAll(PageRequest.of(2,4), null, null, NEWEST).getContent();

        // then
        Assertions.assertThat(result1).contains(problem9, problem8, problem7, problem6).doesNotContain(problem1, problem2, problem3, problem4, problem5);
        Assertions.assertThat(result2).contains(problem2, problem3, problem4, problem5).doesNotContain(problem1, problem6, problem7, problem8, problem9);
        Assertions.assertThat(result3).contains(problem1)
                .doesNotContain(problem2, problem3, problem4, problem5, problem6, problem7, problem8, problem9);
    }

    Member 멤버_저장(Member member) {
        return memberRepository.save(member);
    }

    Problem 문제_저장(Problem problem) {
        return problemRepository.save(problem);
    }

    Solution 풀이_저장(Solution solution) {
        return solutionRepository.save(solution);
    }


}
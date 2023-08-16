package com.prograngers.backend.repository.problem;

import com.prograngers.backend.TestConfig;
import com.prograngers.backend.entity.Member;
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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.prograngers.backend.entity.constants.AlgorithmConstant.*;
import static com.prograngers.backend.entity.constants.DataStructureConstant.*;
import static com.prograngers.backend.fixture.MemberFixture.길가은1;
import static com.prograngers.backend.fixture.ProblemFixture.문제1;
import static com.prograngers.backend.fixture.ProblemFixture.문제2;
import static com.prograngers.backend.fixture.ProblemFixture.문제3;
import static com.prograngers.backend.fixture.SolutionFixture.풀이1;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Slf4j
@Transactional
@Import(TestConfig.class)
class ProblemRepositoryTest {
    @Autowired
    ProblemRepository problemRepository;

    @Autowired
    SolutionRepository solutionRepository;

    @Autowired
    MemberRepository memberRepository;

    @DisplayName("문제 목록 조회시 날짜에 따라 최신순으로 정렬된다")
    @Test
    void 문제_목록_조회_날짜_최신순() {
        // given
        // 회원
        Member member = 멤버_저장(길가은1.아이디_값_지정_멤버_생성(null));

        // 문제
        Problem problem1 = 문제_저장(문제1.아이디_값_지정_문제_생성(null));
        Problem problem2 = 문제_저장(문제2.아이디_값_지정_문제_생성(null));
        Problem problem3 = 문제_저장(문제3.아이디_값_지정_문제_생성(null));

        // 풀이 
        Solution solution1 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem1, member, 0, BFS, QUEUE));
        Solution solution2 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem1, member, 0, BFS, QUEUE));
        Solution solution3 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem1, member, 0, BFS, QUEUE));
        Solution solution4 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem1, member, 0, BFS, QUEUE));
        Solution solution5 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem2, member, 0, BFS, QUEUE));
        Solution solution6 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem2, member, 0, BFS, QUEUE));
        Solution solution7 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem2, member, 0, BFS, QUEUE));
        Solution solution8 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem2, member, 0, BFS, QUEUE));
        Solution solution9 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem3, member, 0, BFS, QUEUE));


        // when
        List<Problem> result = problemRepository.findAll(
                1, null, null, "date"
        );

        // then
        Assertions.assertThat(result.get(0).getTitle()).isEqualTo("문제제목3");
        Assertions.assertThat(result.get(1).getTitle()).isEqualTo("문제제목2");
        Assertions.assertThat(result.get(2).getTitle()).isEqualTo("문제제목1");
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
        Solution solution1 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem1, member, 0, BFS, QUEUE));
        Solution solution2 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem2, member, 0, DFS, QUEUE));
        Solution solution3 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem3, member, 0, BFS, ARRAY));
        Solution solution4 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem4, member, 0, DFS, ARRAY));

        // when
        List<Problem> result1 = problemRepository.findAll(
                1, null, BFS, "date"
        );
        List<Problem> result2 = problemRepository.findAll(
                1, null, DFS, "date"
        );
        List<Problem> result3 = problemRepository.findAll(
                1, QUEUE, BFS, "date"
        );

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
        Solution solution1 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem1, null, 0, null, null));
        Solution solution2 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem2, null, 0, null, null));
        Solution solution3 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem3, null, 0, null, null));
        Solution solution4 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem4, null, 0, null, null));
        Solution solution5 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem5, null, 0, null, null));
        Solution solution6 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem6, null, 0, null, null));
        Solution solution7 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem7, null, 0, null, null));
        Solution solution8 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem8, null, 0, null, null));
        Solution solution9 = 풀이_저장(풀이1.일반_솔루션_생성(null, problem9, null, 0, null, null));

        // when
        List<Problem> result1 = problemRepository.findAll(1, null, null, "date");
        List<Problem> result2 = problemRepository.findAll(2, null, null, "date");
        List<Problem> result3 = problemRepository.findAll(3, null, null, "date");

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
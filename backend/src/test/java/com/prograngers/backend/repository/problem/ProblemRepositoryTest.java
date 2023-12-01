package com.prograngers.backend.repository.problem;

import static com.prograngers.backend.entity.solution.AlgorithmConstant.BFS;
import static com.prograngers.backend.entity.solution.AlgorithmConstant.DFS;
import static com.prograngers.backend.entity.solution.DataStructureConstant.ARRAY;
import static com.prograngers.backend.entity.solution.DataStructureConstant.QUEUE;
import static com.prograngers.backend.entity.solution.LanguageConstant.CPP;
import static com.prograngers.backend.entity.solution.LanguageConstant.JAVA;
import static com.prograngers.backend.entity.solution.LanguageConstant.PYTHON;
import static com.prograngers.backend.entity.sortconstant.SortConstant.NEWEST;
import static com.prograngers.backend.support.fixture.MemberFixture.장지담;
import static com.prograngers.backend.support.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.support.fixture.SolutionFixture.공개_풀이;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import com.prograngers.backend.support.RepositoryTest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

@RepositoryTest
class ProblemRepositoryTest {
    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("문제 목록 조회시 가장 최근 풀이의 날짜에 따라 최신순으로 정렬된다")
    @Test
    void 문제_목록_조회_날짜_최신순() {
        // given
        // 회원
        Member member1 = 저장(장지담.기본_정보_생성());

        // 문제
        Problem problem1 = 백준_문제.기본_정보_생성();
        Problem problem2 = 백준_문제.기본_정보_생성();
        Problem problem3 = 백준_문제.기본_정보_생성();
        Problem problem4 = 백준_문제.기본_정보_생성();

        // 풀이  풀이 9 ~ 1 순으로 최신
        Solution solution1 = 저장(공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now(), BFS, QUEUE, JAVA, 1));
        Solution solution2 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().plusDays(1), DFS, QUEUE, JAVA, 1));
        Solution solution3 = 저장(공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().plusDays(2), BFS, ARRAY, CPP, 1));
        Solution solution4 = 저장(
                공개_풀이.태그_추가_생성(problem2, member1, LocalDateTime.now().plusDays(3), DFS, ARRAY, PYTHON, 1));
        Solution solution5 = 저장(
                공개_풀이.태그_추가_생성(problem2, member1, LocalDateTime.now().plusDays(4), BFS, QUEUE, JAVA, 1));
        Solution solution6 = 저장(
                공개_풀이.태그_추가_생성(problem2, member1, LocalDateTime.now().plusDays(5), DFS, QUEUE, JAVA, 1));
        Solution solution7 = 저장(공개_풀이.태그_추가_생성(problem3, member1, LocalDateTime.now().plusDays(6), BFS, ARRAY, CPP, 1));
        Solution solution8 = 저장(
                공개_풀이.태그_추가_생성(problem3, member1, LocalDateTime.now().plusDays(7), DFS, ARRAY, PYTHON, 1));
        Solution solution9 = 저장(
                공개_풀이.태그_추가_생성(problem3, member1, LocalDateTime.now().plusDays(8), DFS, ARRAY, PYTHON, 1));
        Solution solution10 = 저장(
                공개_풀이.태그_추가_생성(problem4, member1, LocalDateTime.now().plusDays(9), BFS, ARRAY, CPP, 1));
        Solution solution11 = 저장(
                공개_풀이.태그_추가_생성(problem4, member1, LocalDateTime.now().plusDays(10), DFS, ARRAY, PYTHON, 1));
        Solution solution12 = 저장(
                공개_풀이.태그_추가_생성(problem4, member1, LocalDateTime.now().plusDays(11), DFS, ARRAY, PYTHON, 1));
        // when
        List<Problem> result = problemRepository.findAll(
                PageRequest.of(0, 4), null, null, NEWEST
        ).getContent();
        // then
        assertAll(
                () -> assertThat(result).containsExactly(problem4, problem3, problem2, problem1)
        );
    }

    @DisplayName("문제 목록 조회 시 풀이의 알고리즘, 자료구조 필터에 따라 조회한다")
    @Test
    void 문제_목록_필터() {
        // given
        // 회원
        Member member1 = 저장(장지담.기본_정보_생성());

        // 문제
        Problem problem1 = 백준_문제.기본_정보_생성();
        Problem problem2 = 백준_문제.기본_정보_생성();
        Problem problem3 = 백준_문제.기본_정보_생성();
        Problem problem4 = 백준_문제.기본_정보_생성();

        // 풀이
        Solution solution1 = 저장(공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now(), BFS, QUEUE, JAVA, 1));
        Solution solution2 = 저장(
                공개_풀이.태그_추가_생성(problem2, member1, LocalDateTime.now().plusDays(1), DFS, QUEUE, JAVA, 1));
        Solution solution3 = 저장(
                공개_풀이.태그_추가_생성(problem3, member1, LocalDateTime.now().plusDays(2), BFS, ARRAY, JAVA, 1));
        Solution solution4 = 저장(
                공개_풀이.태그_추가_생성(problem4, member1, LocalDateTime.now().plusDays(3), DFS, ARRAY, JAVA, 1));

        // when
        List<Problem> result1 = problemRepository.findAll(
                PageRequest.of(0, 4), null, BFS, NEWEST
        ).getContent();
        List<Problem> result2 = problemRepository.findAll(
                PageRequest.of(0, 4), null, DFS, NEWEST
        ).getContent();
        List<Problem> result3 = problemRepository.findAll(
                PageRequest.of(0, 4), QUEUE, BFS, NEWEST
        ).getContent();

        // then

        assertAll(
                () -> assertThat(result1).containsExactly(problem3, problem1),
                () -> assertThat(result2).containsExactly(problem4, problem2),
                () -> assertThat(result3).containsExactly(problem1)
        );
    }

    @DisplayName("문제 목록 조회 시 페이지에 맞는 문제를 가져온다")
    @Test
    void 문제_목록_조회_페이징() {
        // given
        // 회원
        Member member1 = 저장(장지담.기본_정보_생성());

        // 문제
        Problem problem1 = 백준_문제.기본_정보_생성();
        Problem problem2 = 백준_문제.기본_정보_생성();
        Problem problem3 = 백준_문제.기본_정보_생성();
        Problem problem4 = 백준_문제.기본_정보_생성();
        Problem problem5 = 백준_문제.기본_정보_생성();
        Problem problem6 = 백준_문제.기본_정보_생성();
        Problem problem7 = 백준_문제.기본_정보_생성();
        Problem problem8 = 백준_문제.기본_정보_생성();
        Problem problem9 = 백준_문제.기본_정보_생성();

        //  풀이 :  풀이 9 ~ 1 순서로 최신
        Solution solution1 = 저장(공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now(), BFS, QUEUE, JAVA, 1));
        Solution solution2 = 저장(
                공개_풀이.태그_추가_생성(problem2, member1, LocalDateTime.now().plusDays(1), DFS, QUEUE, JAVA, 1));
        Solution solution3 = 저장(
                공개_풀이.태그_추가_생성(problem3, member1, LocalDateTime.now().plusDays(2), BFS, ARRAY, JAVA, 1));
        Solution solution4 = 저장(
                공개_풀이.태그_추가_생성(problem4, member1, LocalDateTime.now().plusDays(3), DFS, ARRAY, JAVA, 1));
        Solution solution5 = 저장(
                공개_풀이.태그_추가_생성(problem5, member1, LocalDateTime.now().plusDays(4), BFS, QUEUE, JAVA, 1));
        Solution solution6 = 저장(
                공개_풀이.태그_추가_생성(problem6, member1, LocalDateTime.now().plusDays(5), DFS, QUEUE, JAVA, 1));
        Solution solution7 = 저장(
                공개_풀이.태그_추가_생성(problem7, member1, LocalDateTime.now().plusDays(6), BFS, ARRAY, JAVA, 1));
        Solution solution8 = 저장(
                공개_풀이.태그_추가_생성(problem8, member1, LocalDateTime.now().plusDays(7), DFS, ARRAY, JAVA, 1));
        Solution solution9 = 저장(
                공개_풀이.태그_추가_생성(problem9, member1, LocalDateTime.now().plusDays(8), DFS, ARRAY, JAVA, 1));

        // when
        List<Problem> result1 = problemRepository.findAll(PageRequest.of(0, 4), null, null, NEWEST).getContent();
        List<Problem> result2 = problemRepository.findAll(PageRequest.of(1, 4), null, null, NEWEST).getContent();
        List<Problem> result3 = problemRepository.findAll(PageRequest.of(2, 4), null, null, NEWEST).getContent();

        // then
        assertAll(
                () -> assertThat(result1).containsExactly(problem9, problem8, problem7, problem6),
                () -> assertThat(result2).containsExactly(problem5, problem4, problem3, problem2),
                () -> assertThat(result3).contains(problem1)
        );
    }

    private Member 저장(Member member) {
        return memberRepository.save(member);
    }

    private Solution 저장(Solution solution) {
        return solutionRepository.save(solution);
    }
}
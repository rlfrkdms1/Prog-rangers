package com.prograngers.backend.repository.solution;

import com.prograngers.backend.TestConfig;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.problem.ProblemRepository;
import jakarta.persistence.EntityManager;
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

import java.time.LocalDateTime;
import java.util.List;

import static com.prograngers.backend.entity.constants.SortConstant.NEWEST;
import static com.prograngers.backend.entity.constants.SortConstant.SCRAPS;
import static com.prograngers.backend.entity.solution.AlgorithmConstant.BFS;
import static com.prograngers.backend.entity.solution.AlgorithmConstant.DFS;
import static com.prograngers.backend.entity.solution.DataStructureConstant.ARRAY;
import static com.prograngers.backend.entity.solution.DataStructureConstant.LIST;
import static com.prograngers.backend.entity.solution.DataStructureConstant.QUEUE;
import static com.prograngers.backend.entity.solution.LanguageConstant.*;
import static com.prograngers.backend.fixture.MemberFixture.장지담;
import static com.prograngers.backend.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.fixture.SolutionFixture.퍼블릭_풀이;

import static com.prograngers.backend.fixture.SolutionFixture.프라이빗_풀이;
import static org.assertj.core.api.Assertions.*;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Slf4j
@Transactional
@Import(TestConfig.class)
class SolutionRepositoryTest {

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @DisplayName("멤버 이름으로 풀이를 전부 찾을 수 있다")
    @Test
    void 멤버_이름으로_전부_찾기_테스트() {

        // given
        Member member1 = 저장(장지담.기본_정보_생성());
        Member member2 = 저장(장지담.기본_정보_생성());
        Problem problem = 백준_문제.기본_정보_생성();
        // problem은 solution이 저장될 때 같이 저장된다, member는 solution과 cascade 옵션이 걸려있지 않다
        Solution solution1 = 저장(퍼블릭_풀이.기본_정보_생성(problem,member1,LocalDateTime.now(),BFS, LIST,JAVA,1));
        Solution solution2 = 저장(퍼블릭_풀이.기본_정보_생성(problem,member1,LocalDateTime.now(),BFS, LIST,JAVA,1));
        Solution solution3 = 저장(퍼블릭_풀이.기본_정보_생성(problem,member2,LocalDateTime.now(),BFS, LIST,JAVA,1));

        solutionRepository.save(solution1);
        solutionRepository.save(solution2);
        solutionRepository.save(solution3);

        // when
        List<Solution> result = solutionRepository.findAllByMember(member1);

        // then
        assertThat(result.size()).isEqualTo(2);
        assertThat(result).contains(solution1,solution2).doesNotContain(solution3);
    }

    @DisplayName("문제 id로 풀이 목록을 필터링해서 가져올 수 있다")
    @Test
    void 풀이_목록_조회_필터링_문제_id() {
        // given
        Member member1 = 저장(장지담.기본_정보_생성());

        // 문제
        Problem problem1 = 백준_문제.기본_정보_생성();
        Problem problem2 = 백준_문제.기본_정보_생성();


        // 풀이
        Solution solution1 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now(),BFS, LIST,JAVA,1));
        Solution solution2 = 저장(퍼블릭_풀이.기본_정보_생성(problem2,member1,LocalDateTime.now(),BFS, LIST,JAVA,1));

        // when
        List<Solution> result1 = solutionRepository
                .getSolutionList(PageRequest.of(0, 4), problem1.getId(), null, null, null, NEWEST).getContent();
        List<Solution> result2 = solutionRepository
                .getSolutionList(PageRequest.of(0, 4), problem2.getId(), null, null, null, NEWEST).getContent();

        // then
        assertThat(result1).contains(solution1);
        assertThat(result1).doesNotContain(solution2);
        assertThat(result2).contains(solution2);
        assertThat(result2).doesNotContain(solution1);
    }

    @DisplayName("자료구조, 알고리즘으로 풀이 목록을 필터링해서 가져올 수 있다")
    @Test
    void 풀이_목록_조회_필터링_자료구조_알고리즘() {
        // given
        // 회원
        Member member1 = 저장(장지담.기본_정보_생성());

        // 문제
        Problem problem1 = 백준_문제.기본_정보_생성();

        // 풀이
        Solution solution1 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now(),BFS, QUEUE,JAVA,1));
        Solution solution2 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now(),DFS, QUEUE,JAVA,1));
        Solution solution3 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now(),BFS, ARRAY,JAVA,1));
        Solution solution4 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now(),DFS, ARRAY,JAVA,1));


        // when
        List<Solution> result1 = solutionRepository
                .getSolutionList(PageRequest.of(0, 4),  problem1.getId(), null, BFS, null, NEWEST).getContent();
        List<Solution> result2 = solutionRepository
                .getSolutionList(PageRequest.of(0, 4),   problem1.getId(), null, null, QUEUE, NEWEST).getContent();
        List<Solution> result3 = solutionRepository
                .getSolutionList(PageRequest.of(0, 4), problem1.getId(), null, BFS, QUEUE, NEWEST).getContent();

        // then
        assertThat(result1).contains(solution1, solution3).doesNotContain(solution2, solution4);
        assertThat(result2).contains(solution1, solution2).doesNotContain(solution3, solution4);
        assertThat(result3).contains(solution1).doesNotContain(solution2, solution3, solution4);
    }

    @DisplayName("언어로 풀이 목록을 필터링해서 가져올 수 있다")
    @Test
    void 풀이_목록_조회_필터링_언어() {
        // given
        // 회원
        Member member1 = 저장(장지담.기본_정보_생성());
        // 문제
        Problem problem1 = 백준_문제.기본_정보_생성();

        // 풀이
        Solution solution1 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now(),BFS, QUEUE,JAVA,1));
        Solution solution2 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now(),DFS, QUEUE,JAVA,1));
        Solution solution3 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now(),BFS, ARRAY,CPP,1));
        Solution solution4 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now(),DFS, ARRAY,PYTHON,1));

        // when
        List<Solution> result1 = solutionRepository
                .getSolutionList(PageRequest.of(0, 4),  problem1.getId(), JAVA, null, null, NEWEST).getContent();
        List<Solution> result2 = solutionRepository
                .getSolutionList(PageRequest.of(0, 4), problem1.getId(), CPP, null, null, NEWEST).getContent();
        List<Solution> result3 = solutionRepository
                .getSolutionList(PageRequest.of(0, 4), problem1.getId(), PYTHON, null, null, NEWEST).getContent();

        // then
        assertThat(result1).contains(solution1, solution2).doesNotContain(solution3, solution4);
        assertThat(result2).contains(solution3).doesNotContain(solution1, solution2, solution4);
        assertThat(result3).contains(solution4).doesNotContain(solution1, solution2, solution3);
    }

    @DisplayName("페이지에 맞게 문제 목록을 조회할 수 있다")
    @Test
    void 문제_목록_조회_페이지() {
        // given
        // 회원
        Member member1 = 저장(장지담.기본_정보_생성());

        // 문제
        Problem problem1 = 백준_문제.기본_정보_생성();

        // 풀이 : solution9 ~ 1 순서로 최신
        // 풀이
        Solution solution1 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now(),BFS, QUEUE,JAVA,1));
        Solution solution2 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now().plusDays(1),DFS, QUEUE,JAVA,1));
        Solution solution3 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now().plusDays(2),BFS, ARRAY,CPP,1));
        Solution solution4 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now().plusDays(3),DFS, ARRAY,PYTHON,1));
        Solution solution5 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now().plusDays(4),BFS, QUEUE,JAVA,1));
        Solution solution6 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now().plusDays(5),DFS, QUEUE,JAVA,1));
        Solution solution7 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now().plusDays(6),BFS, ARRAY,CPP,1));
        Solution solution8 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now().plusDays(7),DFS, ARRAY,PYTHON,1));
        Solution solution9 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now().plusDays(8),DFS, ARRAY,PYTHON,1));

        // when
        List<Solution> result1 = solutionRepository.getSolutionList(PageRequest.of(0, 4), problem1.getId(), null, null, null, NEWEST).getContent();
        List<Solution> result2 = solutionRepository.getSolutionList(PageRequest.of(1, 4), problem1.getId(), null, null, null, NEWEST).getContent();
        List<Solution> result3 = solutionRepository.getSolutionList(PageRequest.of(2, 4), problem1.getId(), null, null, null, NEWEST).getContent();
        List<Solution> result4 = solutionRepository.getSolutionList(PageRequest.of(3, 4), problem1.getId(), null, null, null, NEWEST).getContent();

        // then
        org.junit.jupiter.api.Assertions
                        .assertAll(
                                ()-> assertThat(result1)
                                        .containsExactly(solution9,solution8,solution7,solution6)
                                        .doesNotContain(solution1,solution2,solution3,solution4,solution5),
                                ()->assertThat(result2)
                                        .containsExactly(solution5,solution4,solution3,solution2)
                                        .doesNotContain(solution1,solution6,solution7,solution8,solution9),
                                ()->assertThat(result3)
                                        .containsExactly(solution1)
                                        .doesNotContain(solution2,solution3,solution4,solution5,solution6,solution7,solution8,solution9),
                                ()->assertThat(result4.size()).isEqualTo(0)
                        );
    }

    @DisplayName("스크랩 수에 따라 문제 목록을 정렬해서 조회할 수 있다")
    @Test
    void 스크랩_문제_정렬(){
        // given
        // 회원
        Member member1 = 저장(장지담.기본_정보_생성());

        // 문제
        Problem problem1 = 백준_문제.기본_정보_생성();
        // 풀이

        /**
         *  solution 3 : 스크랩 한 풀이 3개
         *  solution 2 : 스크랩 한 풀이 2개
         *  solution 1 : 스크랩 한 풀이 1개
         *  스크랩 수가 같으면 날짜수느로 정렬 (solution 9~4 순서)
         */
        Solution solution1 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now(),BFS, QUEUE,JAVA,1));
        Solution solution2 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now().plusDays(1),DFS, QUEUE,JAVA,1));
        Solution solution3 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now().plusDays(2),BFS, ARRAY,CPP,1));
        Solution solution4 = 저장(퍼블릭_풀이.스크랩_생성(problem1,member1,LocalDateTime.now().plusDays(3),DFS, ARRAY,PYTHON,1,solution1));
        Solution solution5 = 저장(퍼블릭_풀이.스크랩_생성(problem1,member1,LocalDateTime.now().plusDays(4),BFS, QUEUE,JAVA,1,solution2));
        Solution solution6 = 저장(퍼블릭_풀이.스크랩_생성(problem1,member1,LocalDateTime.now().plusDays(5),DFS, QUEUE,JAVA,1,solution2));
        Solution solution7 = 저장(퍼블릭_풀이.스크랩_생성(problem1,member1,LocalDateTime.now().plusDays(6),BFS, ARRAY,CPP,1,solution3));
        Solution solution8 = 저장(퍼블릭_풀이.스크랩_생성(problem1,member1,LocalDateTime.now().plusDays(7),DFS, ARRAY,PYTHON,1,solution3));
        Solution solution9 = 저장(퍼블릭_풀이.스크랩_생성(problem1,member1,LocalDateTime.now().plusDays(8),DFS, ARRAY,PYTHON,1,solution3));

        // when
        List<Solution> result1 = solutionRepository
                .getSolutionList(PageRequest.of(0, 4), problem1.getId(), null, null, null, SCRAPS)
                .getContent();
        List<Solution> result2 = solutionRepository
                .getSolutionList(PageRequest.of(1, 4), problem1.getId(), null, null, null, SCRAPS)
                .getContent();
        List<Solution> result3 = solutionRepository
                .getSolutionList(PageRequest.of(2, 4), problem1.getId(), null, null, null, SCRAPS)
                .getContent();

        // then
        Assertions.assertThat(result1).containsExactly(solution3,solution2,solution1,solution9);
        Assertions.assertThat(result2).containsExactly(solution8,solution7,solution6,solution5);
        Assertions.assertThat(result3).containsExactly(solution4);
    }

    @DisplayName("프로필 풀이 찾을 수 있다 (무한스크롤)")
    @Test
    void 프로필_풀이_찾기(){
        // given
        // 회원
        Member member1 = 저장(장지담.기본_정보_생성());

        // 문제
        Problem problem1 = 저장(백준_문제.기본_정보_생성());

        //풀이
        Solution solution1 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now(),DFS,LIST,JAVA,1));
        Solution solution2 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now().plusDays(1),DFS,LIST,JAVA,1));
        Solution solution3 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now().plusDays(2),DFS,LIST,JAVA,1));
        Solution solution4 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now().plusDays(3),DFS,LIST,JAVA,1));
        Solution solution5 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now().plusDays(4),DFS,LIST,JAVA,1));
        Solution solution6 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now().plusDays(5),DFS,LIST,JAVA,1));

        // when
        List<Solution> profileSolutions1 = solutionRepository.findProfileSolutions(member1.getId(), 9223372036854775807L);
        List<Solution> profileSolutions2 = solutionRepository.findProfileSolutions(member1.getId(), solution3.getId());

        // then
        Assertions.assertThat(profileSolutions1).contains(solution4,solution5,solution6).doesNotContain(solution1,solution2,solution3);
        Assertions.assertThat(profileSolutions2).contains(solution1,solution2,solution3).doesNotContain(solution4,solution5,solution6);
    }

    @DisplayName("풀이 목록 조회 시 퍼블릭 풀이만 보인다")
    @Test
    void 퍼블릭_풀이만_보인다(){
        // given
        // 회원
        Member member1 = 저장(장지담.기본_정보_생성());

        // 문제
        Problem problem1 = 저장(백준_문제.기본_정보_생성());

        // 풀이
        Solution solution1 = 저장(퍼블릭_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now(),DFS,LIST,JAVA,1));
        Solution solution2 = 저장(프라이빗_풀이.기본_정보_생성(problem1,member1,LocalDateTime.now(),DFS,LIST,JAVA,1));

        // when
        List<Solution> result = solutionRepository
                .getSolutionList(PageRequest.of(0, 4), problem1.getId(), null, null, null, NEWEST).getContent();

        // then
        Assertions.assertThat(result).contains(solution1).doesNotContain(solution2);
    }

    Member 저장(Member member) {
        return memberRepository.save(member);
    }

    Problem 저장(Problem problem) {
        return problemRepository.save(problem);
    }

    Solution 저장(Solution solution) {
        return solutionRepository.save(solution);
    }

}
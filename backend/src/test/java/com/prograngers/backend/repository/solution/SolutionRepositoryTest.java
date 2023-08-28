package com.prograngers.backend.repository.solution;

import com.prograngers.backend.TestConfig;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.problem.ProblemRepository;
import jakarta.persistence.EntityManager;
import lombok.extern.slf4j.Slf4j;
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
import static com.prograngers.backend.entity.solution.AlgorithmConstant.BFS;
import static com.prograngers.backend.entity.solution.DataStructureConstant.LIST;
import static com.prograngers.backend.entity.solution.LanguageConstant.*;
import static com.prograngers.backend.fixture.MemberFixture.장지담;
import static com.prograngers.backend.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.fixture.SolutionFixture.퍼블릭_풀이;

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

    @Autowired
    private EntityManager em;

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
//
//    @DisplayName("자료구조, 알고리즘으로 풀이 목록을 필터링해서 가져올 수 있다")
//    @Test
//    void 풀이_목록_조회_필터링_자료구조_알고리즘() {
//        // given
//        // 회원
//        Member member = 멤버_저장(길가은1.아이디_값_지정_멤버_생성());
//
//        // 문제
//        Problem problem1 = 문제_저장(문제1.아이디_값_지정_문제_생성());
//
//        // 풀이
//        Solution solution1 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE,JAVA));
//        Solution solution2 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem1, member, DFS, QUEUE,JAVA));
//        Solution solution3 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem1, member, BFS, ARRAY,JAVA));
//        Solution solution4 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem1, member, DFS, ARRAY,JAVA));
//
//        // when
//        List<Solution> result1 = solutionRepository
//                .getSolutionList(PageRequest.of(0, 4),  problem1.getId(), null, BFS, null, NEWEST).getContent();
//        List<Solution> result2 = solutionRepository
//                .getSolutionList(PageRequest.of(0, 4),   problem1.getId(), null, null, QUEUE, NEWEST).getContent();
//        List<Solution> result3 = solutionRepository
//                .getSolutionList(PageRequest.of(0, 4), problem1.getId(), null, BFS, QUEUE, NEWEST).getContent();
//
//        // then
//        assertThat(result1).contains(solution1, solution3).doesNotContain(solution2, solution4);
//        assertThat(result2).contains(solution1, solution2).doesNotContain(solution3, solution4);
//        assertThat(result3).contains(solution1).doesNotContain(solution2, solution3, solution4);
//    }
//
//    @DisplayName("언어로 풀이 목록을 필터링해서 가져올 수 있다")
//    @Test
//    void 풀이_목록_조회_필터링_언어() {
//        // given
//        // 회원
//        Member member = 멤버_저장(길가은1.아이디_값_지정_멤버_생성());
//        // 문제
//        Problem problem1 = 문제_저장(문제1.아이디_값_지정_문제_생성());
//
//        // 풀이
//        Solution solution1 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE, JAVA));
//        Solution solution2 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE, JAVA));
//        Solution solution3 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE, CPP));
//        Solution solution4 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE, PYTHON));
//
//        // when
//        List<Solution> result1 = solutionRepository
//                .getSolutionList(PageRequest.of(0, 4),  problem1.getId(), JAVA, null, null, NEWEST).getContent();
//        List<Solution> result2 = solutionRepository
//                .getSolutionList(PageRequest.of(0, 4), problem1.getId(), CPP, null, null, NEWEST).getContent();
//        List<Solution> result3 = solutionRepository
//                .getSolutionList(PageRequest.of(0, 4), problem1.getId(), PYTHON, null, null, NEWEST).getContent();
//
//        // then
//        assertThat(result1).contains(solution1, solution2).doesNotContain(solution3, solution4);
//        assertThat(result2).contains(solution3).doesNotContain(solution1, solution2, solution4);
//        assertThat(result3).contains(solution4).doesNotContain(solution1, solution2, solution3);
//    }
//
//    @DisplayName("페이지에 맞게 문제 목록을 조회할 수 있다")
//    @Test
//    void 문제_목록_조회_페이지() {
//        // given
//        // 회원
//        Member member = 멤버_저장(길가은1.아이디_값_지정_멤버_생성());
//
//        // 문제
//        Problem problem1 = 문제_저장(문제1.아이디_값_지정_문제_생성());
//
//        // 풀이 : solution9 ~ 1 순서로 최신
//        Solution solution1 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem1, member, BFS, QUEUE,JAVA));
//        Solution solution2 = 풀이_저장(풀이2.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE,JAVA));
//        Solution solution3 = 풀이_저장(풀이3.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE,JAVA));
//        Solution solution4 = 풀이_저장(풀이4.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE,JAVA));
//        Solution solution5 = 풀이_저장(풀이5.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE,JAVA));
//        Solution solution6 = 풀이_저장(풀이6.언어_포함_솔루션_생성(null, problem1, member, BFS, QUEUE,JAVA));
//        Solution solution7 = 풀이_저장(풀이7.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE,JAVA));
//        Solution solution8 = 풀이_저장(풀이8.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE,JAVA));
//        Solution solution9 = 풀이_저장(풀이9.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE,JAVA));
//
//        // when
//        List<Solution> result1 = solutionRepository.getSolutionList(PageRequest.of(0, 4), problem1.getId(), null, null, null, NEWEST).getContent();
//        List<Solution> result2 = solutionRepository.getSolutionList(PageRequest.of(1, 4), problem1.getId(), null, null, null, NEWEST).getContent();
//        List<Solution> result3 = solutionRepository.getSolutionList(PageRequest.of(2, 4), problem1.getId(), null, null, null, NEWEST).getContent();
//        List<Solution> result4 = solutionRepository.getSolutionList(PageRequest.of(3, 4), problem1.getId(), null, null, null, NEWEST).getContent();
//
//        // then
//        org.junit.jupiter.api.Assertions
//                        .assertAll(
//                                ()-> assertThat(result1)
//                                        .containsExactly(solution9,solution8,solution7,solution6)
//                                        .doesNotContain(solution1,solution2,solution3,solution4,solution5),
//                                ()->assertThat(result2)
//                                        .containsExactly(solution5,solution4,solution3,solution2)
//                                        .doesNotContain(solution1,solution6,solution7,solution8,solution9),
//                                ()->assertThat(result3)
//                                        .containsExactly(solution1)
//                                        .doesNotContain(solution2,solution3,solution4,solution5,solution6,solution7,solution8,solution9),
//                                ()->assertThat(result4.size()).isEqualTo(0)
//                        );
//    }
//
//    @DisplayName("스크랩 수에 따라 문제 목록을 정렬해서 조회할 수 있다")
//    @Test
//    void 스크랩_문제_정렬(){
//        // given
//        // 회원
//        Member member = 멤버_저장(길가은1.아이디_값_지정_멤버_생성());
//        // 문제
//        Problem problem1 = 문제_저장(문제1.아이디_값_지정_문제_생성());
//        // 풀이
//        /**
//         *  solution 3 : 스크랩 한 풀이 3개
//         *  solution 2 : 스크랩 한 풀이 2개
//         *  solution 1 : 스크랩 한 풀이 1개
//         *  스크랩 수가 같으면 날짜수느로 정렬 (solution 9~4 순서)
//         */
//        Solution solution1 = 풀이_저장(풀이1.언어_포함_솔루션_생성(null, problem1, member, BFS, QUEUE,JAVA));
//        Solution solution2 = 풀이_저장(풀이2.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE,JAVA));
//        Solution solution3 = 풀이_저장(풀이3.언어_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE,JAVA));
//        Solution solution4 = 풀이_저장(풀이4.스크랩_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE,JAVA,solution1));
//        Solution solution5 = 풀이_저장(풀이5.스크랩_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE,JAVA,solution2));
//        Solution solution6 = 풀이_저장(풀이6.스크랩_포함_솔루션_생성(null, problem1, member, BFS, QUEUE,JAVA,solution2));
//        Solution solution7 = 풀이_저장(풀이7.스크랩_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE,JAVA,solution3));
//        Solution solution8 = 풀이_저장(풀이8.스크랩_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE,JAVA,solution3));
//        Solution solution9 = 풀이_저장(풀이9.스크랩_포함_솔루션_생성(null, problem1, member,  BFS, QUEUE,JAVA,solution3));
//
//        // when
//        List<Solution> result1 = solutionRepository
//                .getSolutionList(PageRequest.of(0, 4), problem1.getId(), null, null, null, SCRAPS)
//                .getContent();
//        List<Solution> result2 = solutionRepository
//                .getSolutionList(PageRequest.of(1, 4), problem1.getId(), null, null, null, SCRAPS)
//                .getContent();
//        List<Solution> result3 = solutionRepository
//                .getSolutionList(PageRequest.of(2, 4), problem1.getId(), null, null, null, SCRAPS)
//                .getContent();
//
//        // then
//        Assertions.assertThat(result1).containsExactly(solution3,solution2,solution1,solution9);
//        Assertions.assertThat(result2).containsExactly(solution8,solution7,solution6,solution5);
//        Assertions.assertThat(result3).containsExactly(solution4);
//    }

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
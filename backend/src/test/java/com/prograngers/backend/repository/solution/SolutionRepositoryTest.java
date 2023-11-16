package com.prograngers.backend.repository.solution;

import static com.prograngers.backend.entity.solution.AlgorithmConstant.BFS;
import static com.prograngers.backend.entity.solution.AlgorithmConstant.DFS;
import static com.prograngers.backend.entity.solution.DataStructureConstant.ARRAY;
import static com.prograngers.backend.entity.solution.DataStructureConstant.LIST;
import static com.prograngers.backend.entity.solution.DataStructureConstant.QUEUE;
import static com.prograngers.backend.entity.solution.LanguageConstant.C;
import static com.prograngers.backend.entity.solution.LanguageConstant.CPP;
import static com.prograngers.backend.entity.solution.LanguageConstant.JAVA;
import static com.prograngers.backend.entity.solution.LanguageConstant.PYTHON;
import static com.prograngers.backend.entity.sortconstant.SortConstant.NEWEST;
import static com.prograngers.backend.entity.sortconstant.SortConstant.SCRAPS;
import static com.prograngers.backend.support.fixture.MemberFixture.길가은;
import static com.prograngers.backend.support.fixture.MemberFixture.이수빈;
import static com.prograngers.backend.support.fixture.MemberFixture.장지담;
import static com.prograngers.backend.support.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.support.fixture.SolutionFixture.공개_풀이;
import static com.prograngers.backend.support.fixture.SolutionFixture.비공개_풀이;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

import com.prograngers.backend.entity.Follow;
import com.prograngers.backend.entity.Likes;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.repository.follow.FollowRepository;
import com.prograngers.backend.repository.likes.LikesRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.problem.ProblemRepository;
import com.prograngers.backend.support.RepositoryTest;
import java.time.LocalDateTime;
import java.util.List;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@Slf4j
@RepositoryTest
class SolutionRepositoryTest {

    @Autowired
    private SolutionRepository solutionRepository;

    @Autowired
    private ProblemRepository problemRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Autowired
    private FollowRepository followRepository;

    @Autowired
    private LikesRepository likesRepository;

    @DisplayName("멤버 이름으로 풀이를 전부 찾을 수 있다")
    @Test
    void 멤버_이름으로_전부_찾기_테스트() {

        // given
        Member member1 = 저장(장지담.기본_정보_생성());
        Member member2 = 저장(장지담.기본_정보_생성());
        Problem problem = 백준_문제.기본_정보_생성();
        // problem은 solution이 저장될 때 같이 저장된다, member는 solution과 cascade 옵션이 걸려있지 않다
        Solution solution1 = 저장(공개_풀이.태그_추가_생성(problem, member1, LocalDateTime.now(), BFS, LIST, JAVA, 1));
        Solution solution2 = 저장(공개_풀이.태그_추가_생성(problem, member1, LocalDateTime.now(), BFS, LIST, JAVA, 1));
        Solution solution3 = 저장(공개_풀이.태그_추가_생성(problem, member2, LocalDateTime.now(), BFS, LIST, JAVA, 1));

        solutionRepository.save(solution1);
        solutionRepository.save(solution2);
        solutionRepository.save(solution3);

        // when
        List<Solution> result = solutionRepository.findAllByMember(member1);

        // then
        assertAll(
                () -> assertThat(result.size()).isEqualTo(2),
                () -> assertThat(result).contains(solution1, solution2).doesNotContain(solution3)
        );
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
        Solution solution1 = 저장(공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now(), BFS, LIST, JAVA, 1));
        Solution solution2 = 저장(공개_풀이.태그_추가_생성(problem2, member1, LocalDateTime.now(), BFS, LIST, JAVA, 1));

        // when
        List<Solution> result1 = solutionRepository
                .getSolutionList(PageRequest.of(0, 4), problem1.getId(), null, null, null, NEWEST).getContent();
        List<Solution> result2 = solutionRepository
                .getSolutionList(PageRequest.of(0, 4), problem2.getId(), null, null, null, NEWEST).getContent();

        // then
        assertAll(
                () -> assertThat(result1).containsExactly(solution1),
                () -> assertThat(result2).containsExactly(solution2)
        );
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
        Solution solution1 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(3), BFS, QUEUE, JAVA, 1));
        Solution solution2 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(2), DFS, QUEUE, JAVA, 1));
        Solution solution3 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(1), BFS, ARRAY, JAVA, 1));
        Solution solution4 = 저장(공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now(), DFS, ARRAY, JAVA, 1));

        // when
        List<Solution> result1 = solutionRepository
                .getSolutionList(PageRequest.of(0, 4), problem1.getId(), null, BFS, null, NEWEST).getContent();
        List<Solution> result2 = solutionRepository
                .getSolutionList(PageRequest.of(0, 4), problem1.getId(), null, null, QUEUE, NEWEST).getContent();
        List<Solution> result3 = solutionRepository
                .getSolutionList(PageRequest.of(0, 4), problem1.getId(), null, BFS, QUEUE, NEWEST).getContent();

        // then
        assertAll(
                () -> assertThat(result1).containsExactly(solution3, solution1),
                () -> assertThat(result2).containsExactly(solution2, solution1),
                () -> assertThat(result3).containsExactly(solution1)
        );
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
        Solution solution1 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(3), BFS, QUEUE, JAVA, 1));
        Solution solution2 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(2), DFS, QUEUE, JAVA, 1));
        Solution solution3 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(1), BFS, ARRAY, CPP, 1));
        Solution solution4 = 저장(공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now(), DFS, ARRAY, PYTHON, 1));

        // when
        List<Solution> result1 = solutionRepository
                .getSolutionList(PageRequest.of(0, 4), problem1.getId(), JAVA, null, null, NEWEST).getContent();
        List<Solution> result2 = solutionRepository
                .getSolutionList(PageRequest.of(0, 4), problem1.getId(), CPP, null, null, NEWEST).getContent();
        List<Solution> result3 = solutionRepository
                .getSolutionList(PageRequest.of(0, 4), problem1.getId(), PYTHON, null, null, NEWEST).getContent();

        // then
        assertAll(
                () -> assertThat(result1).containsExactly(solution2, solution1),
                () -> assertThat(result2).containsExactly(solution3),
                () -> assertThat(result3).containsExactly(solution4)
        );
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
        Solution solution1 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(8), BFS, QUEUE, JAVA, 1));
        Solution solution2 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(7), DFS, QUEUE, JAVA, 1));
        Solution solution3 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(6), BFS, ARRAY, CPP, 1));
        Solution solution4 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(5), DFS, ARRAY, PYTHON, 1));
        Solution solution5 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(4), BFS, QUEUE, JAVA, 1));
        Solution solution6 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(3), DFS, QUEUE, JAVA, 1));
        Solution solution7 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(2), BFS, ARRAY, CPP, 1));
        Solution solution8 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(1), DFS, ARRAY, PYTHON, 1));
        Solution solution9 = 저장(공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now(), DFS, ARRAY, PYTHON, 1));

        // when
        List<Solution> result1 = solutionRepository.getSolutionList(PageRequest.of(0, 4), problem1.getId(), null, null,
                null, NEWEST).getContent();
        List<Solution> result2 = solutionRepository.getSolutionList(PageRequest.of(1, 4), problem1.getId(), null, null,
                null, NEWEST).getContent();
        List<Solution> result3 = solutionRepository.getSolutionList(PageRequest.of(2, 4), problem1.getId(), null, null,
                null, NEWEST).getContent();
        List<Solution> result4 = solutionRepository.getSolutionList(PageRequest.of(3, 4), problem1.getId(), null, null,
                null, NEWEST).getContent();

        // then
        assertAll(
                () -> assertThat(result1)
                        .containsExactly(solution9, solution8, solution7, solution6),
                () -> assertThat(result2)
                        .containsExactly(solution5, solution4, solution3, solution2),
                () -> assertThat(result3)
                        .containsExactly(solution1),
                () -> assertThat(result4.size()).isEqualTo(0)
        );
    }

    @DisplayName("스크랩 수에 따라 문제 목록을 정렬해서 조회할 수 있다")
    @Test
    void 스크랩_문제_정렬() {
        // give
        Member member1 = 저장(장지담.기본_정보_생성());
        Problem problem1 = 백준_문제.기본_정보_생성();
        Solution solution1 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(8), BFS, QUEUE, JAVA, 1));
        Solution solution2 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(7), DFS, QUEUE, JAVA, 1));
        Solution solution3 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(6), BFS, ARRAY, CPP, 1));
        Solution solution4 = 저장(공개_풀이.스크랩_생성(member1, LocalDateTime.now().minusDays(5), 1, solution1));
        Solution solution5 = 저장(공개_풀이.스크랩_생성(member1, LocalDateTime.now().minusDays(4), 1, solution2));
        Solution solution6 = 저장(공개_풀이.스크랩_생성(member1, LocalDateTime.now().minusDays(3), 1, solution2));
        Solution solution7 = 저장(공개_풀이.스크랩_생성(member1, LocalDateTime.now().minusDays(2), 1, solution3));
        Solution solution8 = 저장(공개_풀이.스크랩_생성(member1, LocalDateTime.now().minusDays(1), 1, solution3));
        Solution solution9 = 저장(공개_풀이.스크랩_생성(member1, LocalDateTime.now(), 1, solution3));
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
        assertAll(
                () -> assertThat(result1).containsExactly(solution3, solution2, solution1, solution9),
                () -> assertThat(result2).containsExactly(solution8, solution7, solution6, solution5),
                () -> assertThat(result3).containsExactly(solution4)
        );
    }

    @DisplayName("프로필 풀이 찾을 수 있다 (무한스크롤)")
    @Test
    void 프로필_풀이_찾기() {
        // given
        Member member1 = 저장(장지담.기본_정보_생성());
        Problem problem1 = 저장(백준_문제.기본_정보_생성());
        Solution solution1 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(5), DFS, LIST, JAVA, 1));
        Solution solution2 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(4), DFS, LIST, JAVA, 1));
        Solution solution3 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(3), DFS, LIST, JAVA, 1));
        Solution solution4 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(2), DFS, LIST, JAVA, 1));
        Solution solution5 = 저장(
                공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now().minusDays(1), DFS, LIST, JAVA, 1));
        Solution solution6 = 저장(공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now(), DFS, LIST, JAVA, 1));

        // when
        List<Solution> profileSolutions1 = solutionRepository.findProfileSolutions(member1.getId(),
                9223372036854775807L);
        List<Solution> profileSolutions2 = solutionRepository.findProfileSolutions(member1.getId(), solution3.getId());

        // then
        assertAll(
                () -> assertThat(profileSolutions1).containsExactly(solution6, solution5, solution4),
                () -> assertThat(profileSolutions2).containsExactly(solution3, solution2, solution1)
        );
    }

    @DisplayName("풀이 목록 조회 시 공개 풀이만 보인다")
    @Test
    void 공개_풀이만_보인다() {
        // given
        // 회원
        Member member1 = 저장(장지담.기본_정보_생성());

        // 문제
        Problem problem1 = 저장(백준_문제.기본_정보_생성());

        // 풀이
        Solution solution1 = 저장(공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now(), DFS, LIST, JAVA, 1));
        Solution solution2 = 저장(비공개_풀이.태그_추가_생성(problem1, member1, LocalDateTime.now(), DFS, LIST, JAVA, 1));

        // when
        List<Solution> result = solutionRepository
                .getSolutionList(PageRequest.of(0, 4), problem1.getId(), null, null, null, NEWEST).getContent();

        // then
        assertThat(result).containsExactly(solution1);
    }

    @Test
    @DisplayName("풀이들이 주어졌을 때 해당하는 회원이 작성한 최근 풀이 3개를 정렬해서 가져올 수 있다.")
    void 나의_최근_풀이_조회() {
        Member member = 저장(길가은.기본_정보_생성());
        Problem problem = 저장(백준_문제.기본_정보_생성());

        Solution solution2 = 저장(공개_풀이.기본_정보_생성(problem, member, LocalDateTime.of(2023, 9, 3, 12, 0), JAVA, 1));
        저장(공개_풀이.기본_정보_생성(problem, member, LocalDateTime.of(2023, 8, 3, 12, 0), JAVA, 1));
        Solution solution3 = 저장(공개_풀이.기본_정보_생성(problem, member, LocalDateTime.of(2023, 9, 3, 11, 0), JAVA, 1));
        Solution solution1 = 저장(공개_풀이.기본_정보_생성(problem, member, LocalDateTime.of(2023, 9, 4, 11, 0), JAVA, 1));

        List<Solution> solutionList = solutionRepository.findTop3ByMemberOrderByCreatedAtDesc(member);
        assertThat(solutionList).containsExactly(solution1, solution2, solution3);
    }

    @Test
    @DisplayName("팔로우의 최근 풀이를 5개 조회할 수 있다.")
    void 팔로우의_최근_풀이() {
        Member member1 = 저장(길가은.기본_정보_생성());
        Member member2 = 저장(이수빈.기본_정보_생성());
        Member member3 = 저장(장지담.기본_정보_생성());
        Problem problem = 저장(백준_문제.기본_정보_생성());
        저장(팔로우_생성(member1, member2));
        저장(팔로우_생성(member1, member3));
        Solution solution3 = 저장(공개_풀이.기본_정보_생성(problem, member2, LocalDateTime.of(2023, 9, 3, 12, 0), JAVA, 1));
        Solution solution4 = 저장(공개_풀이.기본_정보_생성(problem, member2, LocalDateTime.of(2023, 9, 2, 12, 0), JAVA, 1));
        Solution solution5 = 저장(공개_풀이.기본_정보_생성(problem, member2, LocalDateTime.of(2023, 8, 2, 12, 0), JAVA, 1));
        저장(공개_풀이.기본_정보_생성(problem, member2, LocalDateTime.of(2023, 7, 2, 12, 0), JAVA, 1));
        Solution solution1 = 저장(공개_풀이.기본_정보_생성(problem, member2, LocalDateTime.of(2023, 9, 5, 12, 0), JAVA, 1));
        Solution solution2 = 저장(공개_풀이.기본_정보_생성(problem, member3, LocalDateTime.of(2023, 9, 4, 12, 0), JAVA, 1));
        List<Solution> solutionList = solutionRepository.findFollowingsRecentSolutions(member1.getId(), 5);
        assertThat(solutionList).containsExactly(solution1, solution2, solution3, solution4, solution5);
    }

    @Test
    @DisplayName("회원이 주어졌을 때 해당 회원이 작성한 주어진 달의 풀이들을 조회할 수 있다.")
    void 월별_풀이_조회() {
        Member member1 = 저장(길가은.기본_정보_생성());
        Member member2 = 저장(장지담.기본_정보_생성());
        Problem problem = 저장(백준_문제.기본_정보_생성());

        Integer solution1 = 저장(
                공개_풀이.기본_정보_생성(problem, member1, LocalDateTime.of(2023, 9, 3, 12, 0), JAVA, 1)).getCreatedAt()
                .getDayOfMonth();
        Integer solution2 = 저장(
                공개_풀이.기본_정보_생성(problem, member1, LocalDateTime.of(2023, 9, 5, 12, 0), JAVA, 1)).getCreatedAt()
                .getDayOfMonth();
        Integer solution3 = 저장(
                공개_풀이.기본_정보_생성(problem, member1, LocalDateTime.of(2023, 9, 3, 11, 0), JAVA, 1)).getCreatedAt()
                .getDayOfMonth();
        Integer solution4 = 저장(
                공개_풀이.기본_정보_생성(problem, member1, LocalDateTime.of(2023, 8, 4, 11, 0), JAVA, 1)).getCreatedAt()
                .getDayOfMonth();
        Integer solution5 = 저장(
                공개_풀이.기본_정보_생성(problem, member2, LocalDateTime.of(2023, 9, 10, 11, 0), JAVA, 1)).getCreatedAt()
                .getDayOfMonth();

        List<Integer> dayOfWriteSolution1 = solutionRepository.findAllByMonth(member1.getId(), 9);
        List<Integer> dayOfWriteSolution2 = solutionRepository.findAllByMonth(member2.getId(), 9);
        assertAll(
                () -> assertThat(dayOfWriteSolution1).contains(solution1, solution2, solution3)
                        .doesNotContain(solution4, solution5),
                () -> assertThat(dayOfWriteSolution2).contains(solution5)
                        .doesNotContain(solution1, solution2, solution3, solution4)
        );
    }

    @Test
    @DisplayName("회원이 주어졌을 때 해당 회원이 작성한 풀이들의 목록을 날짜순으로 조회할 수 있다.")
    void 내_풀이_목록_조회() {
        Member member1 = 저장(길가은.기본_정보_생성());
        Member member2 = 저장(장지담.기본_정보_생성());
        Problem problem = 저장(백준_문제.기본_정보_생성());

        저장(공개_풀이.기본_정보_생성(problem, member1, LocalDateTime.now().minusDays(1), JAVA, 1));
        Solution solution = 저장(공개_풀이.기본_정보_생성(problem, member1, LocalDateTime.now(), JAVA, 1));
        저장(공개_풀이.기본_정보_생성(problem, member2, LocalDateTime.now(), JAVA, 1));

        Page<Solution> solutions = solutionRepository.getMyList(PageRequest.of(0, 1), null, null, null, null, null,
                member1.getId());

        assertThat(solutions).containsExactly(solution);
    }

    @Test
    @DisplayName("회원과 키워드가 주어졌을 때 해당 회원이 작성한 풀이를 검색해서 목록을 조회할 수 있다.")
    void 내_풀이_목록_검색() {
        Member member1 = 저장(길가은.기본_정보_생성());
        Problem problem = 저장(백준_문제.기본_정보_생성());
        String keyword = "찾는 제목";
        Solution solution2 = 저장(
                공개_풀이.풀이_제목_지정_생성(keyword + "1", problem, member1, LocalDateTime.now().minusDays(1), JAVA, 1));
        Solution solution1 = 저장(공개_풀이.풀이_제목_지정_생성("1" + keyword, problem, member1, LocalDateTime.now(), JAVA, 1));
        저장(비공개_풀이.풀이_제목_지정_생성("다른 제목", problem, member1, LocalDateTime.now(), JAVA, 1));

        Page<Solution> solutions1 = solutionRepository.getMyList(PageRequest.of(0, 1), keyword, null, null, null, null,
                member1.getId());
        Page<Solution> solutions2 = solutionRepository.getMyList(PageRequest.of(1, 1), keyword, null, null, null, null,
                member1.getId());

        assertAll(
                () -> assertThat(solutions1).containsExactly(solution1),
                () -> assertThat(solutions2).containsExactly(solution2)
        );
    }

    @Test
    @DisplayName("회원과 키워드가 주어졌을 때 해당 회원이 작성한 풀이를 검색해서 목록을 조회할 수 있다.")
    void 내_풀이_목록_태그() {
        Member member1 = 저장(길가은.기본_정보_생성());
        Problem problem = 저장(백준_문제.기본_정보_생성());
        Solution solution1 = 저장(
                공개_풀이.태그_추가_생성(problem, member1, LocalDateTime.now().minusDays(1), AlgorithmConstant.DFS,
                        DataStructureConstant.ARRAY, JAVA, 1));
        Solution solution2 = 저장(
                공개_풀이.태그_추가_생성(problem, member1, LocalDateTime.now().minusDays(2), AlgorithmConstant.DFS, LIST, JAVA,
                        1));
        Solution solution3 = 저장(
                공개_풀이.태그_추가_생성(problem, member1, LocalDateTime.now(), AlgorithmConstant.DFS, LIST, JAVA, 1));
        저장(공개_풀이.태그_추가_생성(problem, member1, LocalDateTime.now().minusDays(3), AlgorithmConstant.BINARY_SEARCH, null, C,
                1));
        Solution solution4 = 저장(
                공개_풀이.태그_추가_생성(problem, member1, LocalDateTime.now().minusDays(4), AlgorithmConstant.QUICK_SORT, ARRAY,
                        PYTHON, 1));

        Page<Solution> solutions1 = solutionRepository.getMyList(PageRequest.of(0, 1), null, null, DFS, LIST, null,
                member1.getId());
        Page<Solution> solutions2 = solutionRepository.getMyList(PageRequest.of(0, 1), null, null, null, ARRAY, null,
                member1.getId());
        Page<Solution> solutions3 = solutionRepository.getMyList(PageRequest.of(0, 1), null, null, BFS, ARRAY, null,
                member1.getId());
        Page<Solution> solutions4 = solutionRepository.getMyList(PageRequest.of(1, 1), null, null, DFS, LIST, null,
                member1.getId());
        Page<Solution> solutions5 = solutionRepository.getMyList(PageRequest.of(0, 2), null, JAVA, null, null, null,
                member1.getId());
        Page<Solution> solutions6 = solutionRepository.getMyList(PageRequest.of(0, 2), null, PYTHON, null, null, null,
                member1.getId());

        assertAll(
                () -> assertThat(solutions1).containsExactly(solution3),
                () -> assertThat(solutions2).containsExactly(solution1),
                () -> assertThat(solutions3).isEmpty(),
                () -> assertThat(solutions4).containsExactly(solution2),
                () -> assertThat(solutions5).containsExactly(solution3, solution1),
                () -> assertThat(solutions6).containsExactly(solution4)
        );
    }

    @Test
    @DisplayName("회원과 키워드가 주어졌을 때 해당 회원이 작성한 풀이의 난이도를 필터링해서 목록을 조회할 수 있다.")
    void 내_풀이_목록_난이도() {
        Member member1 = 저장(길가은.기본_정보_생성());
        Problem problem = 저장(백준_문제.기본_정보_생성());
        Solution solution1 = 저장(공개_풀이.기본_정보_생성(problem, member1, LocalDateTime.now().minusDays(1), JAVA, 1));
        Solution solution2 = 저장(비공개_풀이.기본_정보_생성(problem, member1, LocalDateTime.now(), JAVA, 3));

        Page<Solution> solutions1 = solutionRepository.getMyList(PageRequest.of(0, 1), null, null, null, null, 1,
                member1.getId());
        Page<Solution> solutions2 = solutionRepository.getMyList(PageRequest.of(0, 1), null, null, null, null, 3,
                member1.getId());

        assertAll(
                () -> assertThat(solutions1).containsExactly(solution1),
                () -> assertThat(solutions2).containsExactly(solution2)
        );
    }

    @Test
    @DisplayName("문제가 주어졌을 때 좋아요 순으로 상위 6개의 풀이를 정렬해서 가져올 수 있다")
    void 문제_풀이_좋아요_정렬_가져오기() {
        Member member1 = 저장(장지담.기본_정보_생성());
        Member member2 = 저장(장지담.기본_정보_생성());
        Member member3 = 저장(장지담.기본_정보_생성());
        Member member4 = 저장(장지담.기본_정보_생성());

        Problem problem = 저장(백준_문제.기본_정보_생성());

        Solution solution4 = 저장(공개_풀이.기본_정보_생성(problem, member1, LocalDateTime.now(), JAVA, 1));
        Solution solution2 = 저장(공개_풀이.기본_정보_생성(problem, member1, LocalDateTime.now(), JAVA, 1));
        Solution solution3 = 저장(공개_풀이.기본_정보_생성(problem, member1, LocalDateTime.now(), JAVA, 1));
        Solution solution1 = 저장(공개_풀이.기본_정보_생성(problem, member1, LocalDateTime.now(), JAVA, 1));

        저장(좋아요_생성(member1, solution1));
        저장(좋아요_생성(member2, solution1));
        저장(좋아요_생성(member3, solution1));
        저장(좋아요_생성(member4, solution1));

        저장(좋아요_생성(member1, solution2));
        저장(좋아요_생성(member2, solution2));
        저장(좋아요_생성(member3, solution2));

        저장(좋아요_생성(member1, solution3));
        저장(좋아요_생성(member2, solution3));

        저장(좋아요_생성(member1, solution4));

        List<Solution> result = solutionRepository.findTopLimitsSolutionOfProblemOrderByLikesDesc(problem, 3);

        assertThat(result).containsExactly(solution1, solution2, solution3);
    }

    @Test
    @DisplayName("문제에 해당하는 풀이를 날짜 오름차순으로 가져온다")
    void findAllByProblemOrderByCreatedAtDescTest() {
        //given
        Member member = 저장(장지담.기본_정보_생성());
        Problem problem1 = 저장(백준_문제.기본_정보_생성());
        Problem problem2 = 저장(백준_문제.기본_정보_생성());

        Solution solution3 = 저장(공개_풀이.기본_정보_생성(problem1, member, LocalDateTime.now().plusDays(1), JAVA, 1));
        Solution solution1 = 저장(공개_풀이.기본_정보_생성(problem1, member, LocalDateTime.now().plusDays(3), JAVA, 1));
        Solution solution2 = 저장(공개_풀이.기본_정보_생성(problem1, member, LocalDateTime.now().plusDays(2), JAVA, 1));

        //when
        List<Solution> result = solutionRepository.findAllByProblemOrderByCreatedAtAsc(problem1);

        //then
        assertThat(result).containsExactly(solution3, solution2, solution1);
    }

    private Likes 좋아요_생성(Member member, Solution solution) {
        return Likes.builder()
                .member(member)
                .solution(solution)
                .build();
    }

    private Follow 팔로우_생성(Member member1, Member member2) {
        return Follow
                .builder()
                .followerId(member1.getId())
                .followingId(member2.getId())
                .build();
    }

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

    private Likes 저장(Likes likes) {
        return likesRepository.save(likes);
    }
}
package com.prograngers.backend.repository.likes;

import com.prograngers.backend.TestConfig;
import com.prograngers.backend.entity.Likes;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.problem.ProblemRepository;
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

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static com.prograngers.backend.entity.solution.AlgorithmConstant.DFS;
import static com.prograngers.backend.entity.solution.DataStructureConstant.LIST;
import static com.prograngers.backend.entity.solution.LanguageConstant.JAVA;
import static com.prograngers.backend.fixture.MemberFixture.장지담;
import static com.prograngers.backend.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.fixture.SolutionFixture.퍼블릭_풀이;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Slf4j
@Transactional
@Import(TestConfig.class)
class LikesRepositoryTest {

    @Autowired
    LikesRepository likesRepository;
    @Autowired
    MemberRepository memberRepository;
    @Autowired
    ProblemRepository problemRepository;
    @Autowired
    SolutionRepository solutionRepository;

    @Test
    @DisplayName("풀이로 좋아요를 조회한다")
    void 풀이로_좋아요_조회(){
        // given
        Member member = 저장(장지담.기본_정보_생성());
        Problem problem = 저장(백준_문제.기본_정보_생성());
        Solution solution1 = 저장(퍼블릭_풀이.기본_정보_생성(problem, member, LocalDateTime.now(), DFS, LIST, JAVA, 1));
        Solution solution2 = 저장(퍼블릭_풀이.기본_정보_생성(problem, member, LocalDateTime.now(), DFS, LIST, JAVA, 1));

        Likes like1 = 저장(Likes.builder().member(member).solution(solution1).build());
        Likes like2 = 저장(Likes.builder().member(member).solution(solution1).build());
        Likes like3 = 저장(Likes.builder().member(member).solution(solution2).build());
        Likes like4 = 저장(Likes.builder().member(member).solution(solution2).build());

        // when
        List<Likes> allBySolution1 = likesRepository.findAllBySolution(solution1);
        List<Likes> allBySolution2 = likesRepository.findAllBySolution(solution2);

        // then
        Assertions.assertThat(allBySolution1).contains(like1,like2).doesNotContain(like3,like4);
        Assertions.assertThat(allBySolution2).contains(like3,like4).doesNotContain(like1,like2);
    }

    @Test
    @DisplayName("풀이와 회원으로 좋아요를 조회한다")
    void 풀이와_회원으로_좋아요_조회(){
        // given
        Member member1 = 저장(장지담.기본_정보_생성());
        Member member2 = 저장(장지담.기본_정보_생성());
        Problem problem = 저장(백준_문제.기본_정보_생성());
        Solution solution1 = 저장(퍼블릭_풀이.기본_정보_생성(problem, member1, LocalDateTime.now(), DFS, LIST, JAVA, 1));
        Solution solution2 = 저장(퍼블릭_풀이.기본_정보_생성(problem, member1, LocalDateTime.now(), DFS, LIST, JAVA, 1));

        Likes like1 = 저장(Likes.builder().member(member1).solution(solution1).build());
        Likes like2 = 저장(Likes.builder().member(member2).solution(solution1).build());
        Likes like3 = 저장(Likes.builder().member(member1).solution(solution2).build());
        Likes like4 = 저장(Likes.builder().member(member2).solution(solution2).build());

        // when
        Likes byMemberAndSolution1 = likesRepository.findByMemberAndSolution(member1, solution1).get();
        Likes byMemberAndSolution2 = likesRepository.findByMemberAndSolution(member2, solution2).get();

        // then
        Assertions.assertThat(byMemberAndSolution1).isEqualTo(like1);
        Assertions.assertThat(byMemberAndSolution2).isEqualTo(like4);
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

    Likes 저장(Likes like) {return likesRepository.save(like);}


}
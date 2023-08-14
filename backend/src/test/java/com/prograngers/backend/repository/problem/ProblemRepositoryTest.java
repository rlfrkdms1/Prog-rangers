package com.prograngers.backend.repository.problem;

import com.prograngers.backend.TestConfig;
import com.prograngers.backend.entity.Member;
import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
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
        Member member = 길가은1.getMember();
        memberRepository.save(member);

        // 문제
        Problem problem1 = 문제1.아이디_값_지정_문제_생성(null);
        Problem problem2 = 문제2.아이디_값_지정_문제_생성(null);
        Problem problem3 = 문제3.아이디_값_지정_문제_생성(null);

        problemRepository.save(problem1);
        problemRepository.save(problem2);
        problemRepository.save(problem3);

        // 풀이 : 문제 - 풀이를 join해서 가져오기 때문에 problem.s
        Solution solution1 = 풀이1.일반_솔루션_생성(null, problem1, member, 0, AlgorithmConstant.BFS, DataStructureConstant.QUEUE);
        Solution solution2 = 풀이1.일반_솔루션_생성(null, problem1, member, 0, AlgorithmConstant.BFS, DataStructureConstant.QUEUE);
        Solution solution3 = 풀이1.일반_솔루션_생성(null, problem1, member, 0, AlgorithmConstant.BFS, DataStructureConstant.QUEUE);
        Solution solution4 = 풀이1.일반_솔루션_생성(null, problem1, member, 0, AlgorithmConstant.BFS, DataStructureConstant.QUEUE);
        Solution solution5 = 풀이1.일반_솔루션_생성(null, problem1, member, 0, AlgorithmConstant.BFS, DataStructureConstant.QUEUE);
        Solution solution6 = 풀이1.일반_솔루션_생성(null, problem2, member, 0, AlgorithmConstant.BFS, DataStructureConstant.QUEUE);
        Solution solution7 = 풀이1.일반_솔루션_생성(null, problem2, member, 0, AlgorithmConstant.BFS, DataStructureConstant.QUEUE);
        Solution solution8 = 풀이1.일반_솔루션_생성(null, problem2, member, 0, AlgorithmConstant.BFS, DataStructureConstant.QUEUE);
        Solution solution9 = 풀이1.일반_솔루션_생성(null, problem3, member, 0, AlgorithmConstant.BFS, DataStructureConstant.QUEUE);
        solutionRepository.save(solution1);
        solutionRepository.save(solution2);
        solutionRepository.save(solution3);
        solutionRepository.save(solution4);
        solutionRepository.save(solution5);
        solutionRepository.save(solution6);
        solutionRepository.save(solution7);
        solutionRepository.save(solution8);
        solutionRepository.save(solution9);


        List<Problem> findAll = problemRepository.findAll();
        log.info("findAll.size() = {}",findAll.size());

        // when
        List<Problem> result = problemRepository.searchByAlgorithmAndDataStructureOrderByDateDesc(
                1, null, null, "date"
        );

        log.info("result.size() = {}",result.size());

        for (Problem problem : result) {
            log.info("problem title : {}",problem.getTitle());
        }

        // then
        Assertions.assertThat(result.get(0).getTitle()).isEqualTo("문제제목3");
        Assertions.assertThat(result.get(1).getTitle()).isEqualTo("문제제목2");
        Assertions.assertThat(result.get(2).getTitle()).isEqualTo("문제제목1");
    }


}
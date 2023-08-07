package com.prograngers.backend.repository;

import com.prograngers.backend.entity.Member;
import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.constants.JudgeConstant;
import com.prograngers.backend.entity.constants.LevelConstant;
import com.prograngers.backend.exception.notfound.SolutionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Slf4j
@Transactional
class SolutionRepositoryTest {
    @Autowired
    private SolutionRepository solutionRepository;


    @DisplayName("풀이를 저장할 수 있다")
    @Test
    void 저장_테스트() {

        // given
        Solution solution = Solution.builder()
                .level(LevelConstant.THREE)
                .title("풀이 제목")
                .algorithm(AlgorithmConstant.BFS)
                .dataStructure(DataStructureConstant.ARRAY)
                .code("int a=10")
                .description("풀이 설명")
                .date(LocalDate.now())
                .problem(new Problem(null, "문제", "링크", JudgeConstant.백준))
                .build();

        // when
        Solution saved = solutionRepository.save(solution);

        //then
        Assertions.assertThat(saved).isEqualTo(solution);
    }

    @DisplayName("풀이를 저장할 수 있다")
    @Test
    void 수정_테스트() {
        // given
        Solution solution = Solution.builder()
                .level(LevelConstant.THREE)
                .title("풀이 제목")
                .algorithm(AlgorithmConstant.BFS)
                .dataStructure(DataStructureConstant.ARRAY)
                .code("int a=10")
                .description("풀이 설명")
                .date(LocalDate.now())
                .problem(new Problem(null, "문제", "링크", JudgeConstant.백준))
                .build();

        Solution saved = solutionRepository.save(solution);

        saved.updateDescription("수정한 설명입니다");
        saved.updateAlgorithm(AlgorithmConstant.DFS);

        // when
        Solution updated = solutionRepository.save(saved);

        // then
        Assertions.assertThat(updated).isEqualTo(saved);
    }

    @DisplayName("풀이를 삭제할 수 있다")
    @Test
    void 삭제_테스트() {
        // given
        Solution solution = Solution.builder()
                .level(LevelConstant.THREE)
                .title("풀이 제목")
                .algorithm(AlgorithmConstant.BFS)
                .dataStructure(DataStructureConstant.ARRAY)
                .code("int a=10")
                .description("풀이 설명")
                .date(LocalDate.now())
                .problem(new Problem(null, "문제", "링크", JudgeConstant.백준))
                .build();
        Solution saved = solutionRepository.save(solution);
        log.info("saved id : {}", saved.getId());

        // when
        solutionRepository.delete(solution);

        // then
        org.junit.jupiter.api.Assertions.assertThrows(SolutionNotFoundException.class,
                () -> {
                    solutionRepository.findById(solution.getId()).orElseThrow(() -> new SolutionNotFoundException());
                });
    }

    @DisplayName("멤버 이름으로 풀이를 전부 찾을 수 있다")
    @Test
    void 멤버_이름으로_전부_찾기_테스트(){

        Member member1 = Member.builder()
                .name("장지담")
                .build();

        Member member2 = Member.builder()
                .name("길가은")
                .build();

        Solution solution1 = Solution.builder()
                .member(member1)
                .level(LevelConstant.THREE)
                .title("풀이 제목")
                .algorithm(AlgorithmConstant.BFS)
                .dataStructure(DataStructureConstant.ARRAY)
                .code("int a=10")
                .description("풀이 설명")
                .date(LocalDate.now())
                .problem(new Problem(null, "문제", "링크", JudgeConstant.백준))
                .build();
        Solution solution2 = Solution.builder()
                .member(member1)
                .level(LevelConstant.THREE)
                .title("풀이 제목")
                .algorithm(AlgorithmConstant.BFS)
                .dataStructure(DataStructureConstant.ARRAY)
                .code("int a=10")
                .description("풀이 설명")
                .date(LocalDate.now())
                .problem(new Problem(null, "문제", "링크", JudgeConstant.백준))
                .build();
        Solution solution3 = Solution.builder()
                .member(member2)
                .level(LevelConstant.THREE)
                .title("풀이 제목")
                .algorithm(AlgorithmConstant.BFS)
                .dataStructure(DataStructureConstant.ARRAY)
                .code("int a=10")
                .description("풀이 설명")
                .date(LocalDate.now())
                .problem(new Problem(null, "문제", "링크", JudgeConstant.백준))
                .build();
        solutionRepository.save(solution1);
        solutionRepository.save(solution2);
        solutionRepository.save(solution3);

        List<Solution> result = solutionRepository.findAllByMember(member1);
        Assertions.assertThat(result.size()).isEqualTo(2);
    }

}
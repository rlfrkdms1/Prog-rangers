package com.prograngers.backend.repository;

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

@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Slf4j
class SolutionRepositoryTest {
    @Autowired
    private SolutionRepository solutionRepository;

    @Test
    @DisplayName("저장_테스트")
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

    @Test
    @Transactional
    @DisplayName("수정 테스트")
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

    @Test
    @Transactional
    @DisplayName("삭제 테스트")
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

}
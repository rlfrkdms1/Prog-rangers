package com.prograngers.backend.repository;

import com.prograngers.backend.entity.*;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.constants.JudgeConstant;
import com.prograngers.backend.entity.constants.LevelConstant;
import com.prograngers.backend.exception.notfound.SolutionNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
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
    void 정상_입력_저장_테스트(){

//        // given
//         Solution solution = SOLUTION.getSolution(1L,null,null,null);

        // given
        Solution solution = Solution.builder()
                .level(LevelConstant.THREE)
                .title("풀이 제목")
                .algorithm(new Algorithm(null, AlgorithmConstant.BFS))
                .dataStructure(new DataStructure(null, DataStructureConstant.ARRAY))
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
    void 솔루션_수정_테스트(){
        // given
        Solution solution = Solution.builder()
                .level(LevelConstant.THREE)
                .title("풀이 제목")
                .algorithm(new Algorithm(null, AlgorithmConstant.BFS))
                .dataStructure(new DataStructure(null, DataStructureConstant.ARRAY))
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
    void 솔루션_삭제_테스트(){
        // given
        Solution solution = Solution.builder()
                .level(LevelConstant.THREE)
                .title("풀이 제목")
                .algorithm(new Algorithm(null, AlgorithmConstant.BFS))
                .dataStructure(new DataStructure(null, DataStructureConstant.ARRAY))
                .code("int a=10")
                .description("풀이 설명")
                .date(LocalDate.now())
                .problem(new Problem(null, "문제", "링크", JudgeConstant.백준))
                .build();
        Solution saved = solutionRepository.save(solution);
        log.info("saved id : {}",saved.getId());

        // when
        solutionRepository.delete(solution);

        // then
        org.junit.jupiter.api.Assertions.assertThrows(SolutionNotFoundException.class,
                ()->{
                    solutionRepository.findById(solution.getId()).orElseThrow(()->new SolutionNotFoundException());
                });
    }

}
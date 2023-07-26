package com.prograngers.backend.repository;

import com.prograngers.backend.entity.*;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDate;


@SpringBootTest
@Slf4j
class SolutionRepositoryTest {
    @Autowired
    private SolutionRepository solutionRepository;

    @Test
    @Transactional
    void 정상_입력_저장_테스트(){
        // given
        Solution solution = Solution.builder()
                .level(Level.THREE)
                .title("풀이 제목")
                .algorithm(new Algorithm(null, "알고리즘명"))
                .dataStructure(new DataStructure(null, "자료구조명"))
                .code("int a=10")
                .description("풀이 설명")
                .date(LocalDate.now())
                .problem(new Problem(null, "문제", "링크", "저지명"))
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
                .level(Level.THREE)
                .title("풀이 제목")
                .algorithm(new Algorithm(null, "알고리즘명"))
                .dataStructure(new DataStructure(null, "자료구조명"))
                .code("int a=10")
                .description("풀이 설명")
                .date(LocalDate.now())
                .problem(new Problem(null, "문제", "링크", "저지명"))
                .build();
        Solution saved = solutionRepository.save(solution);
        log.info("saved id : {}",saved.getId());


        Solution patchSolution = Solution.builder()
                .id(saved.getId())
                .level(saved.getLevel())
                .title("풀이 제목 수정")
                .algorithm(saved.getAlgorithm())
                .dataStructure(saved.getDataStructure())
                .code(saved.getCode())
                .description(saved.getDescription())
                .date(saved.getDate())
                .problem(saved.getProblem())
                .build();
        log.info("patchSolution id : {}",patchSolution.getId());

        // when
        Solution patchedSolution = solutionRepository.save(patchSolution);
        log.info("patchedSolution id : {}",patchedSolution.getId());

        // then
        Assertions.assertThat(patchedSolution.getTitle()).isEqualTo(patchSolution.getTitle());
    }



}
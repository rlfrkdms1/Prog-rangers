package com.prograngers.backend.service;

import com.prograngers.backend.entity.*;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;


@SpringBootTest
class SolutionServiceTest {

    @Autowired
    private SolutionService solutionService;

    @Test
    void 저장_테스트(){
        // given
        Solution solution = Solution.builder()
                .level(Levels.THREE)
                .title("풀이 제목")
                .algorithm(new Algorithm(null, "알고리즘명"))
                .dataStructure(new DataStructure(null, "자료구조명"))
                .code("int a=10")
                .description("풀이 설명")
                .date(LocalDate.now())
                .problem(new Problem(null, "문제", "링크", "저지명"))
                .build();

        // when
        Solution saved = solutionService.save(solution);

        // then
        Assertions.assertThat(saved).isEqualTo(solution);

    }

    @Test
    void 수정_테스트(){
        // given
        Solution solution = Solution.builder()
                .level(Levels.THREE)
                .title("풀이 제목")
                .algorithm(new Algorithm(null, "알고리즘명"))
                .dataStructure(new DataStructure(null, "자료구조명"))
                .code("int a=10")
                .description("풀이 설명")
                .date(LocalDate.now())
                .problem(new Problem(null, "문제", "링크", "저지명"))
                .build();

        Long solutionId = solution.getId();

        Solution tou = Solution.builder()
                .level(Levels.THREE)
                .title("풀이 제목")
                .algorithm(new Algorithm(null, "알고리즘명"))
                .dataStructure(new DataStructure(null, "자료구조명"))
                .code("int a=10")
                .description("풀이 설명")
                .date(LocalDate.now())
                .problem(new Problem(null, "문제", "링크", "저지명"))
                .build();


        // when


        // then
    }






}
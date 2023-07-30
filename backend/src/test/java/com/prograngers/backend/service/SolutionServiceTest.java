package com.prograngers.backend.service;

import com.prograngers.backend.dto.SolutionPatchRequest;
import com.prograngers.backend.entity.*;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.constants.JudgeConstant;
import com.prograngers.backend.entity.constants.LevelConstant;
import com.prograngers.backend.repository.SolutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.Optional;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
@Slf4j
class SolutionServiceTest {
    // Mokito , SpringBootTest 빼고
    @Mock
   private  SolutionRepository solutionRepository;
    @InjectMocks
    private SolutionService solutionService;

    @Test
    void 저장_테스트(){
        // given
        Solution solution = Solution.builder()
                .id(1L)
                .problem(new Problem(null,"문제제목","https://www.acmicpc.net/problem/1000", JudgeConstant.백준))
                .member(new Member(null,"이름","닉네임","email@naver.com",null,null,"password","01012345678"))
                .title("풀이제목")
                .isPublic(true)
                .code("코드")
                .description("설명")
                .scraps(0)
                .scrapId(null)
                .date(LocalDate.now())
                .algorithm(new Algorithm(null, AlgorithmConstant.BFS))
                .dataStructure(new DataStructure(null, DataStructureConstant.ARRAY))
                .level(LevelConstant.THREE)
                .build();

        given(solutionRepository.save(solution)).willReturn(solution);

        // when
        Solution saved = solutionService.save(solution);

        // then
        Assertions.assertThat(saved).isEqualTo(solution);
    }

    @Test
    void 수정_테스트(){
        // given
        Solution solution = Solution.builder()
                .id(1L)
                .problem(new Problem(null,"문제제목","https://www.acmicpc.net/problem/1000", JudgeConstant.백준))
                .member(new Member(null,"이름","닉네임","email@naver.com",null,null,"password","01012345678"))
                .title("풀이제목")
                .isPublic(true)
                .code("코드")
                .description("설명")
                .scraps(0)
                .scrapId(null)
                .date(LocalDate.now())
                .algorithm(new Algorithm(null, AlgorithmConstant.BFS))
                .dataStructure(new DataStructure(null, DataStructureConstant.ARRAY))
                .level(LevelConstant.THREE)
                .build();

        given(solutionRepository.save(solution)).willReturn(solution);
        solutionService.save(solution);
        given(solutionRepository.findById(solution.getId())).willReturn(Optional.ofNullable(solution));

        // when
        solution.updateTitle("수정 제목");
        SolutionPatchRequest solutionPatchRequest = new SolutionPatchRequest(
                "수정 제목", AlgorithmConstant.BFS, DataStructureConstant.ARRAY,"코드","설명");
        Solution updated = solutionService.update(solution.getId(),solutionPatchRequest);

        // then
        Assertions.assertThat(updated).isEqualTo(solution);
    }

    @Test
    void 삭제_테스트(){
        // given
        Solution solution = Solution.builder()
                .id(1L)
                .problem(new Problem(null,"문제제목","https://www.acmicpc.net/problem/1000", JudgeConstant.백준))
                .member(new Member(null,"이름","닉네임","email@naver.com",null,null,"password","01012345678"))
                .title("풀이제목")
                .isPublic(true)
                .code("코드")
                .description("설명")
                .scraps(0)
                .scrapId(null)
                .date(LocalDate.now())
                .algorithm(new Algorithm(null, AlgorithmConstant.BFS))
                .dataStructure(new DataStructure(null, DataStructureConstant.ARRAY))
                .level(LevelConstant.THREE)
                .build();

        given(solutionRepository.save(solution)).willReturn(solution);
        given(solutionRepository.findById(solution.getId())).willReturn(Optional.ofNullable(solution));

        Solution saved = solutionService.save(solution);
        solutionService.delete(saved.getId());

        verify(solutionRepository).delete(saved);

    }

}
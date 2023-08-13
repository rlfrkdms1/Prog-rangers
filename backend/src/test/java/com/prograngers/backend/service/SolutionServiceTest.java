package com.prograngers.backend.service;

import com.prograngers.backend.dto.solution.ScarpSolutionRequest;
import com.prograngers.backend.dto.solution.SolutionPatchRequest;
import com.prograngers.backend.dto.solution.SolutionRequest;
import com.prograngers.backend.entity.Member;
import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.constants.LevelConstant;
import com.prograngers.backend.exception.notfound.SolutionNotFoundException;
import com.prograngers.backend.repository.comment.CommentRepository;
import com.prograngers.backend.repository.problem.ProblemRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


import java.time.LocalDate;
import java.util.Optional;

import static com.prograngers.backend.fixture.MemberFixture.길가은1;
import static com.prograngers.backend.fixture.ProblemFixture.문제1;
import static com.prograngers.backend.fixture.SolutionFixture.풀이1;
import static com.prograngers.backend.fixture.SolutionFixture.풀이2;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@Slf4j
class SolutionServiceTest {

    @Mock
    private SolutionRepository solutionRepository;
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ProblemRepository problemRepository;

    @InjectMocks
    private SolutionService solutionService;

    @DisplayName("풀이를 저장할 수 있다")
    @Test
    void 저장_테스트() {
        // given
        Member member = 길가은1.getMember();
        Problem problem = 문제1.getProblem();
        Solution solution = 풀이1.일반_솔루션_생성(1L, problem, member,  0, AlgorithmConstant.BFS, DataStructureConstant.ARRAY);
        given(solutionRepository.save(any())).willReturn(solution);

        // when
        Long saveId = solutionService.save(SolutionRequest.toDto(solution));

        // then
        Assertions.assertThat(saveId).isEqualTo(solution.getId());
    }

    @DisplayName("스크랩 해서 풀이를 저장할 수 있다")
    @Test
    void 스크랩_저장_테스트(){
        // given
        Member member = 길가은1.getMember();
        Problem problem = 문제1.getProblem();
        Solution solution = 풀이1.일반_솔루션_생성(1L, problem, member, 0, AlgorithmConstant.BFS, DataStructureConstant.ARRAY);

        ScarpSolutionRequest request = new ScarpSolutionRequest("스크랩풀이", "스크랩풀이설명", LevelConstant.FIVE);
        Solution made = request.toEntity(solution);

        when(solutionRepository.save(any())).thenReturn(solution).thenReturn(made);
        when(solutionRepository.findById(any())).
                thenReturn(Optional.ofNullable(solution)).
                thenReturn(Optional.ofNullable(made));

        solutionService.save(SolutionRequest.toDto(solution));

        // when
        Long scrapedId = solutionService.saveScrap(solution.getId(), request);

        // then
        Assertions.assertThat(solutionService.findById(scrapedId).getTitle()).isEqualTo("스크랩풀이");
    }

    @DisplayName("풀이를 수정할 수 있다")
    @Test
    void 수정_테스트() {
        // given
        Member member = 길가은1.getMember();
        Problem problem = 문제1.getProblem();
        Solution solution = 풀이1.일반_솔루션_생성(1L, problem, member, 0, AlgorithmConstant.BFS, DataStructureConstant.ARRAY);
        Solution updateExpected = 풀이2.일반_솔루션_생성(1L, problem, member, 0, AlgorithmConstant.BFS, DataStructureConstant.ARRAY);

        when(solutionRepository.save(any())).thenReturn(solution).thenReturn(updateExpected);
        when(solutionRepository.findById(any())).thenReturn(Optional.ofNullable(updateExpected));
        solutionRepository.save(solution);

        // when
        SolutionPatchRequest solutionPatchRequest = new SolutionPatchRequest("풀이제목2",AlgorithmConstant.BFS,DataStructureConstant.STACK,"풀이코드2","풀이설명2");
        Long updatedId = solutionService.update(solution.getId(), solutionPatchRequest);

        // then
        Assertions.assertThat(solutionRepository.findById(updatedId).orElse(null).getTitle())
                .isEqualTo("풀이제목2");
    }

    @DisplayName("풀이를 삭제할 수 있다")
    @Test
    void 삭제_테스트() {
        // given
        Member member = 길가은1.getMember();
        Problem problem = 문제1.getProblem();
        Solution solution = 풀이1.일반_솔루션_생성(1L, problem, member, 0, AlgorithmConstant.BFS, DataStructureConstant.ARRAY);

        when(solutionRepository.save(any())).thenReturn(solution);
        when(solutionRepository.findById(any())).thenReturn(Optional.ofNullable(solution));

        solutionRepository.save(solution);

        // when
        solutionService.delete(solution.getId());

        verify(solutionRepository).delete(solution);
    }

    @DisplayName("존재하지 않는 풀이를 조회하면 예외가 발생한다")
    @Test
    void 없는_풀이_조회(){
        // then
        org.junit.jupiter.api.Assertions.assertThrows(SolutionNotFoundException.class
        , ()->solutionService.findById(1L)
                );
    }
}
package com.prograngers.backend.service;

import com.prograngers.backend.dto.solution.reqeust.ScarpSolutionPostRequest;
import com.prograngers.backend.dto.solution.reqeust.SolutionPatchRequest;
import com.prograngers.backend.dto.solution.reqeust.SolutionPostRequest;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.exception.notfound.SolutionNotFoundException;
import com.prograngers.backend.repository.comment.CommentRepository;
import com.prograngers.backend.repository.member.MemberRepository;
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
class SolutionListSolutionServiceTest {

    @Mock
    private SolutionRepository solutionRepository;
    @Mock
    private CommentRepository commentRepository;

    @Mock
    private ProblemRepository problemRepository;

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private SolutionService solutionService;

    @DisplayName("풀이를 저장할 수 있다")
    @Test
    void 저장_테스트() {
        // given
        Member member = 길가은1.getMember();
        Problem problem = 문제1.getProblem();
        Solution solution = 풀이1.일반_솔루션_생성(1L, problem, member, 0, AlgorithmConstant.BFS, DataStructureConstant.ARRAY);
        given(solutionRepository.save(any())).willReturn(solution);
        when(memberRepository.findById(member.getId())).thenReturn(Optional.ofNullable(member));

        // when
        Long saveId = solutionService.save(SolutionPostRequest.from(solution), member.getId());

        // then
        Assertions.assertThat(saveId).isEqualTo(solution.getId());
    }

    @DisplayName("스크랩 해서 풀이를 저장할 수 있다")
    @Test
    void 스크랩_저장_테스트() {
        // given
        Member member = 길가은1.아이디_값_지정_멤버_생성();
        Problem problem = 문제1.getProblem();
        Solution solution = 풀이1.일반_솔루션_생성(1L, problem, member, 0, AlgorithmConstant.BFS, DataStructureConstant.ARRAY);

        ScarpSolutionPostRequest request = new ScarpSolutionPostRequest("스크랩풀이", "스크랩풀이설명", 5);
        Solution made = request.toSolution(solution);

        when(memberRepository.findById(member.getId())).thenReturn(Optional.ofNullable(member));
        when(solutionRepository.save(any())).thenReturn(solution).thenReturn(made);
        when(solutionRepository.findById(any())).
                thenReturn(Optional.ofNullable(solution)).
                thenReturn(Optional.ofNullable(made));

        solutionService.save(SolutionPostRequest.from(solution), member.getId());

        // when
        Long scrapedId = solutionService.saveScrap(solution.getId(), request,member.getId());

        // then
        Assertions.assertThat(solutionService.findById(scrapedId).getTitle()).isEqualTo("스크랩풀이");
    }

    @DisplayName("풀이를 수정할 수 있다")
    @Test
    void 수정_테스트() {
        // given
        Member member = 길가은1.아이디_값_지정_멤버_생성();
        Problem problem = 문제1.getProblem();
        Solution solution = 풀이1.일반_솔루션_생성(1L, problem, member, 0, AlgorithmConstant.BFS, DataStructureConstant.ARRAY);
        Solution updateExpected = 풀이2.일반_솔루션_생성(1L, problem, member, 0, AlgorithmConstant.BFS, DataStructureConstant.ARRAY);

        when(solutionRepository.save(any())).thenReturn(solution).thenReturn(updateExpected);
        when(solutionRepository.findById(any())).thenReturn(Optional.ofNullable(updateExpected));
        when(memberRepository.findById(member.getId())).thenReturn(Optional.ofNullable(member));
        solutionRepository.save(solution);

        // when
        SolutionPatchRequest solutionPatchRequest = new SolutionPatchRequest("풀이제목2", AlgorithmConstant.BFS, DataStructureConstant.STACK, "풀이코드2", "풀이설명2",5);
        Long updatedId = solutionService.update(solution.getId(), solutionPatchRequest, member.getId());

        // then
        Assertions.assertThat(solutionRepository.findById(updatedId).orElse(null).getTitle())
                .isEqualTo("풀이제목2");
    }

    @DisplayName("풀이를 삭제할 수 있다")
    @Test
    void 삭제_테스트() {
        // given
        Member member = 길가은1.아이디_값_지정_멤버_생성();
        Problem problem = 문제1.getProblem();
        Solution solution = 풀이1.일반_솔루션_생성(1L, problem, member, 0, AlgorithmConstant.BFS, DataStructureConstant.ARRAY);

        when(solutionRepository.save(any())).thenReturn(solution);
        when(solutionRepository.findById(any())).thenReturn(Optional.ofNullable(solution));
        when(memberRepository.findById(member.getId())).thenReturn(Optional.ofNullable(member));

        solutionRepository.save(solution);

        // when
        solutionService.delete(solution.getId(), member.getId());

        verify(solutionRepository).delete(solution);
    }

    @DisplayName("존재하지 않는 풀이를 조회하면 예외가 발생한다")
    @Test
    void 없는_풀이_조회() {
        // then
        org.junit.jupiter.api.Assertions.assertThrows(SolutionNotFoundException.class
            , () -> solutionService.findById(1L)
        );
    }
}
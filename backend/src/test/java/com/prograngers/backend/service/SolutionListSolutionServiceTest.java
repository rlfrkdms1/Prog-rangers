package com.prograngers.backend.service;

import com.prograngers.backend.dto.solution.reqeust.ScarpSolutionPostRequest;
import com.prograngers.backend.dto.solution.reqeust.SolutionPatchRequest;
import com.prograngers.backend.dto.solution.reqeust.SolutionPostRequest;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.exception.badrequest.PrivateSolutionException;
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


import java.time.LocalDateTime;
import java.util.Optional;

import static com.prograngers.backend.entity.solution.AlgorithmConstant.*;
import static com.prograngers.backend.entity.solution.AlgorithmConstant.BFS;
import static com.prograngers.backend.entity.solution.AlgorithmConstant.DFS;
import static com.prograngers.backend.entity.solution.DataStructureConstant.*;
import static com.prograngers.backend.entity.solution.DataStructureConstant.LIST;
import static com.prograngers.backend.entity.solution.DataStructureConstant.QUEUE;
import static com.prograngers.backend.entity.solution.LanguageConstant.JAVA;
import static com.prograngers.backend.fixture.MemberFixture.장지담;
import static com.prograngers.backend.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.fixture.SolutionFixture.퍼블릭_풀이;
import static com.prograngers.backend.fixture.SolutionFixture.프라이빗_풀이;
import static org.junit.jupiter.api.Assertions.*;
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

    @DisplayName("스크랩 해서 풀이를 저장할 수 있다")
    @Test
    void 스크랩_저장_테스트() {
        // given
        Member member = 장지담.기본_정보_생성();
        Problem problem = 백준_문제.기본_정보_생성();
        Solution solution = 퍼블릭_풀이.기본_정보_생성(problem,member, LocalDateTime.now(),DFS,LIST,JAVA,1);

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
        Member member = 장지담.기본_정보_생성();
        Problem problem = 백준_문제.기본_정보_생성();
        Solution solution = 퍼블릭_풀이.기본_정보_생성(problem,member,LocalDateTime.now(),BFS, QUEUE,JAVA,1);
        Solution updateExpected = 퍼블릭_풀이.기본_정보_생성(problem,member,LocalDateTime.now(),DFS, QUEUE,JAVA,1);

        when(solutionRepository.save(any())).thenReturn(solution).thenReturn(updateExpected);
        when(solutionRepository.findById(any())).thenReturn(Optional.ofNullable(updateExpected));
        when(memberRepository.findById(member.getId())).thenReturn(Optional.ofNullable(member));
        solutionRepository.save(solution);

        // when
        SolutionPatchRequest solutionPatchRequest = new SolutionPatchRequest("풀이제목", DFS, STACK, "풀이코드", "풀이설명",5);
        Long updatedId = solutionService.update(solution.getId(), solutionPatchRequest, member.getId());

        // then
        Assertions.assertThat(solutionRepository.findById(updatedId).orElse(null).getAlgorithm())
                .isEqualTo(DFS);
    }

    @DisplayName("존재하지 않는 풀이를 조회하면 예외가 발생한다")
    @Test
    void 없는_풀이_조회() {
        // then
        assertThrows(SolutionNotFoundException.class
            , () -> solutionService.findById(1L)
        );
    }

    @DisplayName("내 풀이가 아닌 프라이빗 풀이를 조회하면 예외가 발생한다")
    @Test
    void 프라이빗_풀이_조회_예외_발생(){
        // given
        Member member1 = 장지담.아이디_지정_생성(1L);
        Member member2 = 장지담.아이디_지정_생성(2L);
        Problem problem = 백준_문제.기본_정보_생성();
        Solution solution = 프라이빗_풀이.기본_정보_생성(problem,member1,LocalDateTime.now(),BFS, QUEUE,JAVA,1);

        // when
        when(solutionRepository.findById(solution.getId())).thenReturn(Optional.ofNullable(solution));
        when(memberRepository.findById(member2.getId())).thenReturn(Optional.ofNullable(member2));

        // then
        assertThrows(PrivateSolutionException.class,()->solutionService.getSolutionDetail(solution.getId(),member2.getId()));
    }

}
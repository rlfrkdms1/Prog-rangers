package com.prograngers.backend.service;

import com.prograngers.backend.dto.solution.reqeust.ScarpSolutionPostRequest;
import com.prograngers.backend.dto.solution.reqeust.SolutionPatchRequest;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.AlgorithmConstant;
import com.prograngers.backend.entity.solution.DataStructureConstant;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.badrequest.PrivateSolutionException;
import com.prograngers.backend.exception.notfound.SolutionNotFoundException;
import com.prograngers.backend.exception.unauthorization.MemberUnAuthorizedException;
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

import static com.prograngers.backend.entity.solution.AlgorithmConstant.BFS;
import static com.prograngers.backend.entity.solution.AlgorithmConstant.DFS;
import static com.prograngers.backend.entity.solution.DataStructureConstant.*;
import static com.prograngers.backend.entity.solution.DataStructureConstant.ARRAY;
import static com.prograngers.backend.entity.solution.DataStructureConstant.LIST;
import static com.prograngers.backend.entity.solution.DataStructureConstant.QUEUE;
import static com.prograngers.backend.entity.solution.LanguageConstant.JAVA;
import static com.prograngers.backend.support.fixture.MemberFixture.장지담;
import static com.prograngers.backend.support.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.support.fixture.SolutionFixture.공개_풀이;
import static com.prograngers.backend.support.fixture.SolutionFixture.비공개_풀이;
import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
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

    @Mock
    private MemberRepository memberRepository;

    @InjectMocks
    private SolutionService solutionService;

    @DisplayName("스크랩 해서 풀이를 저장할 수 있다")
    @Test
    void 스크랩_저장_테스트() {
        // given
        Member member = 장지담.아이디_지정_생성(1L);
        Problem problem = 백준_문제.기본_정보_생성();
        // 스크랩 당할 풀이 scrapTarget
        Solution scrapTarget = 공개_풀이.아이디_지정_생성(1L,problem,member, LocalDateTime.now(),DFS,LIST,JAVA,1);

        // 스크랩 요청 생성
        ScarpSolutionPostRequest request = 스크랩_풀이_생성_요청_생성("풀이제목","풀이설명", 5);

        // 스크랩의 결과로 만들어져야 하는 풀이
        Solution scrapResult = request.toSolution(scrapTarget, member);

        when(solutionRepository.findById(any())).thenReturn(Optional.of(scrapTarget));
        when(memberRepository.findById(any())).thenReturn(Optional.of(member));
        when(solutionRepository.save(any())).thenReturn(Optional.of(scrapResult).get());

        // when
        solutionService.saveScrap(scrapTarget.getId(), request, member.getId());

        // then
        verify(solutionRepository,times(1)).save(any());
    }

    @DisplayName("존재하지 않는 풀이를 조회하면 예외가 발생한다")
    @Test
    void 없는_풀이_조회() {
        // then
        assertThrows(SolutionNotFoundException.class
            , () -> solutionService.findById(1L)
        );
    }

    @DisplayName("내 풀이가 아닌 비공개 풀이를 조회하면 예외가 발생한다")
    @Test
    void 비공개_풀이_조회_예외_발생(){
        // given
        Member member1 = 장지담.아이디_지정_생성(1L);
        Member member2 = 장지담.아이디_지정_생성(2L);
        Problem problem = 백준_문제.기본_정보_생성();
        Solution solution = 비공개_풀이.기본_정보_생성(problem,member1,LocalDateTime.now(),BFS, QUEUE,JAVA,1);

        // when
        when(solutionRepository.findById(solution.getId())).thenReturn(Optional.ofNullable(solution));
        when(memberRepository.findById(member2.getId())).thenReturn(Optional.ofNullable(member2));

        // then
        assertThrows(PrivateSolutionException.class,()->solutionService.getSolutionDetail(solution.getId(),member2.getId()));
    }

    @DisplayName("내 풀이가 아닌 풀이를 수정하려고 하면 예외가 발생한다")
    @Test
    void 내_풀이_아닌_풀이_수정_시_예외_발생(){
        // given
        Member member1 = 장지담.아이디_지정_생성(1L);
        Member member2 = 장지담.아이디_지정_생성(2L);
        Problem problem = 백준_문제.기본_정보_생성();
        Solution solution1 = 공개_풀이.아이디_지정_생성(1L, problem, member1, LocalDateTime.now(), BFS, QUEUE, JAVA, 1);

        SolutionPatchRequest request = 풀이_수정_요청_생성("수정제목", BFS, ARRAY, "수정코드", "수정설명", 1);

        when(memberRepository.findById(any())).thenReturn(Optional.of(member2));
        when(solutionRepository.findById(any())).thenReturn(Optional.of(solution1));

        // when , then
        assertThrows(
                MemberUnAuthorizedException.class,
                ()->solutionService.update(solution1.getId(),request,member2.getId()
        ));
    }

    @DisplayName("내 풀이가 아닌 풀이를 삭제하려고 하면 예외가 발생한다")
    @Test
    void 내_풀이_아닌_풀이_삭제_시_예외_발생(){
        // given
        Member member1 = 장지담.아이디_지정_생성(1L);
        Member member2 = 장지담.아이디_지정_생성(2L);
        Problem problem = 백준_문제.기본_정보_생성();
        Solution solution1 = 공개_풀이.아이디_지정_생성(1L, problem, member1, LocalDateTime.now(), BFS, QUEUE, JAVA, 1);

        when(memberRepository.findById(any())).thenReturn(Optional.of(member2));
        when(solutionRepository.findById(any())).thenReturn(Optional.of(solution1));

        // when , then
        assertThrows(
                MemberUnAuthorizedException.class,
                ()->solutionService.delete(solution1.getId(),member2.getId()
                ));
    }


    ScarpSolutionPostRequest 스크랩_풀이_생성_요청_생성(String title, String description, int level){
        return new ScarpSolutionPostRequest(title,description,level);
    }

    private static SolutionPatchRequest 풀이_수정_요청_생성(
            String title, AlgorithmConstant algorithm, DataStructureConstant dataStructure,
            String  code, String description, int level) {
        return new SolutionPatchRequest(title,algorithm,dataStructure,code,description,level);
    }

}
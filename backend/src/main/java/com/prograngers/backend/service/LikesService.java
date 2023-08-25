package com.prograngers.backend.service;

import com.prograngers.backend.entity.Likes;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.exception.badrequest.LikesAlreadyExistsException;
import com.prograngers.backend.exception.notfound.LikesNotFoundException;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.exception.notfound.SolutionNotFoundException;
import com.prograngers.backend.repository.likes.LikesRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class LikesService {
    private final LikesRepository likesRepository;
    private final SolutionRepository solutionRepository;
    private final MemberRepository memberRepository;

    public void pushLike(Long memberId, Long solutionId) {
        Solution targetSolution = solutionRepository.findById(solutionId).orElseThrow(SolutionNotFoundException::new);
        Member targetMember = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Likes targetLikes = likesRepository.findByMemberAndSolution(targetMember, targetSolution).orElse(null);
        if (targetLikes!=null){
            throw new LikesAlreadyExistsException();
        }
        Likes likes = Likes.builder()
                .solution(targetSolution)
                .member(targetMember)
                .build();
        likesRepository.save(likes);
    }

    public void cancelLike(Long memberId, Long solutionId) {
        Solution targetSolution = solutionRepository.findById(solutionId).orElseThrow(SolutionNotFoundException::new);
        Member targetMember = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Likes targetLikes = likesRepository.findByMemberAndSolution(targetMember, targetSolution).orElseThrow(LikesNotFoundException::new);
        likesRepository.delete(targetLikes);
    }
}

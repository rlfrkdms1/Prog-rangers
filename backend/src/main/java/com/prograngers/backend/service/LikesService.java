package com.prograngers.backend.service;

import com.prograngers.backend.entity.Likes;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.badrequest.LikesAlreadyExistsException;
import com.prograngers.backend.exception.notfound.LikesNotFoundException;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.exception.notfound.SolutionNotFoundException;
import com.prograngers.backend.exception.unauthorization.MemberUnAuthorizedException;
import com.prograngers.backend.repository.likes.LikesRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class LikesService {
    private final LikesRepository likesRepository;
    private final SolutionRepository solutionRepository;
    private final MemberRepository memberRepository;

    public void pushLike(Long memberId, Long solutionId) {
        Solution targetSolution = getTargetSolution(solutionId);
        Member targetMember = getTargetMember(memberId);
        validLikeAlreadyExists(targetSolution, targetMember);
        Likes likes = Likes.builder()
                .solution(targetSolution)
                .member(targetMember)
                .build();
        likesRepository.save(likes);
    }

    public void cancelLike(Long memberId, Long solutionId) {
        Solution targetSolution = getTargetSolution(solutionId);
        Member targetMember = getTargetMember(memberId);
        Likes targetLikes = getLikesBySolutionAndMember(targetSolution, targetMember);
        validMemberAuthorization(targetMember, targetLikes);
        likesRepository.delete(targetLikes);
    }

    private void validMemberAuthorization(Member targetMember, Likes targetLikes) {
        if (targetLikes.getMember().getId() != targetMember.getId()) {
            throw new MemberUnAuthorizedException();
        }
    }

    private Likes getLikesBySolutionAndMember(Solution targetSolution, Member targetMember) {
        return likesRepository.findByMemberAndSolution(targetMember, targetSolution)
                .orElseThrow(LikesNotFoundException::new);
    }

    private Solution getTargetSolution(Long solutionId) {
        return solutionRepository.findById(solutionId).orElseThrow(SolutionNotFoundException::new);
    }

    private Member getTargetMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    private Optional<Likes> getTargetLikes(Solution targetSolution, Member targetMember) {
        return likesRepository.findByMemberAndSolution(targetMember, targetSolution);
    }

    private void validLikeAlreadyExists(Solution targetSolution, Member targetMember) {
        if (getTargetLikes(targetSolution, targetMember).isPresent()) {
            throw new LikesAlreadyExistsException();
        }
    }


}

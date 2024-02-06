package com.prograngers.backend.service;

import static com.prograngers.backend.exception.errorcode.AuthErrorCode.UNAUTHORIZED_MEMBER;
import static com.prograngers.backend.exception.errorcode.LikeErrorCode.ALREADY_EXISTS_LIKE;
import static com.prograngers.backend.exception.errorcode.LikeErrorCode.NOT_FOUND_LIKE;
import static com.prograngers.backend.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.prograngers.backend.exception.errorcode.SolutionErrorCode.SOLUTION_NOT_FOUND;

import com.prograngers.backend.entity.Likes;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.AlreadyExistsException;
import com.prograngers.backend.exception.NotFoundException;
import com.prograngers.backend.exception.UnAuthorizationException;
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
            throw new UnAuthorizationException(UNAUTHORIZED_MEMBER);
        }
    }

    private Likes getLikesBySolutionAndMember(Solution targetSolution, Member targetMember) {
        return likesRepository.findByMemberAndSolution(targetMember, targetSolution)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_LIKE));
    }

    private Solution getTargetSolution(Long solutionId) {
        return solutionRepository.findById(solutionId).orElseThrow(() -> new NotFoundException(SOLUTION_NOT_FOUND));
    }

    private Member getTargetMember(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND));
    }

    private Optional<Likes> getTargetLikes(Solution targetSolution, Member targetMember) {
        return likesRepository.findByMemberAndSolution(targetMember, targetSolution);
    }

    private void validLikeAlreadyExists(Solution targetSolution, Member targetMember) {
        if (getTargetLikes(targetSolution, targetMember).isPresent()) {
            throw new AlreadyExistsException(ALREADY_EXISTS_LIKE);
        }
    }


}

package com.prograngers.backend.service;

import static com.prograngers.backend.exception.errorcode.FollowErrorCode.ALREADY_FOLLOWING;
import static com.prograngers.backend.exception.errorcode.FollowErrorCode.NOT_FOUND_FOLLOW;
import static com.prograngers.backend.exception.errorcode.FollowErrorCode.SELF_FOLLOW;
import static com.prograngers.backend.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;

import com.prograngers.backend.dto.follow.response.ShowFollowListResponse;
import com.prograngers.backend.entity.Follow;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.AlreadyExistsException;
import com.prograngers.backend.exception.InvalidValueException;
import com.prograngers.backend.exception.NotFoundException;
import com.prograngers.backend.repository.follow.FollowRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class FollowService {

    private final FollowRepository followRepository;
    private final MemberRepository memberRepository;
    private final SolutionRepository solutionRepository;

    @Transactional
    public void follow(Long followerId, Long followingId) {
        validNotSelf(followerId, followingId);
        validFollowerAndFollowingExist(followerId, followingId);
        validNotFollow(followerId, followingId);
        Follow follow = Follow.builder().followingId(followingId).followerId(followerId).build();
        followRepository.save(follow);
    }

    private void validNotSelf(Long followerId, Long followingId) {
        if (followerId.equals(followingId)) {
            throw new InvalidValueException(SELF_FOLLOW);
        }
    }

    private void validNotFollow(Long followerId, Long followingId) {
        if (followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            throw new AlreadyExistsException(ALREADY_FOLLOWING);
        }
    }

    private void validFollowerAndFollowingExist(Long followerId, Long followingId) {
        if (!memberRepository.existsById(followerId) || !memberRepository.existsById(followingId)) {
            throw new NotFoundException(MEMBER_NOT_FOUND);
        }
    }

    @Transactional
    public void unfollow(Long followerId, Long followingId) {
        validFollowerAndFollowingExist(followerId, followingId);
        Follow follow = findFollowRecord(followerId, followingId);
        followRepository.delete(follow);
    }

    private Follow findFollowRecord(Long followerId, Long followingId) {
        return followRepository.findByFollowerIdAndFollowingId(followerId, followingId)
                .orElseThrow(() -> new NotFoundException(NOT_FOUND_FOLLOW));
    }

    public ShowFollowListResponse getFollowList(Long memberId, Long recommendMemberCount) {
        List<Member> followingList = memberRepository.findAllByFollower(memberId);
        List<Member> followerList = memberRepository.findAllByFollowing(memberId);
        List<Member> recommendedFollows = getRecommendedFollows(
                solutionRepository.findOneRecentSolutionByMemberId(memberId), memberId, recommendMemberCount);
        return ShowFollowListResponse.of(followingList, followerList, recommendedFollows);
    }

    private List<Member> getRecommendedFollows(Solution recentSolution, Long memberId, Long recommendMemberCount) {
        if (recentSolution != null) {
            List<Member> limitRecommendedMembers = memberRepository.getLimitRecommendedMembers(
                    recentSolution.getProblem(), recommendMemberCount, memberId);
            if (limitRecommendedMembers.size() < recommendMemberCount) {
                addScarceMembers(memberId, limitRecommendedMembers, recommendMemberCount);
            }
            return limitRecommendedMembers;
        }
        return memberRepository.getLimitRecommendedMembers(null, recommendMemberCount, memberId);
    }

    private void addScarceMembers(Long memberId, List<Member> limitRecommendedMembers, Long recommendMemberCount) {
        List<Member> membersOrderByFollow = memberRepository.getOtherMembersOrderByFollow(memberId);
        for (int memberNumber = 0; memberNumber < membersOrderByFollow.size(); memberNumber++) {
            Member member = membersOrderByFollow.get(memberNumber);
            if (!limitRecommendedMembers.contains(member)) {
                limitRecommendedMembers.add(member);
            }
            if (limitRecommendedMembers.size() == recommendMemberCount) {
                break;
            }
        }
    }
}

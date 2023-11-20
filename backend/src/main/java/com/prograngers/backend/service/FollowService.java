package com.prograngers.backend.service;

import com.prograngers.backend.dto.follow.response.ShowFollowListResponse;
import com.prograngers.backend.entity.Follow;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.badrequest.AlreadyFollowingException;
import com.prograngers.backend.exception.notfound.FollowNotFoundException;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
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
    private final Long RECOMMENDED_MEMBER_COUNT = 10L;

    @Transactional
    public void follow(Long followerId, Long followingId) {
        validFollowerAndFollowingExist(followerId, followingId);
        validNotFollow(followerId, followingId);
        Follow follow = Follow.builder().followingId(followingId).followerId(followerId).build();
        followRepository.save(follow);
    }

    private void validNotFollow(Long followerId, Long followingId) {
        if (followRepository.existsByFollowerIdAndFollowingId(followerId, followingId)) {
            throw new AlreadyFollowingException();
        }
    }

    private void validFollowerAndFollowingExist(Long followerId, Long followingId) {
        if (!memberRepository.existsById(followerId) || !memberRepository.existsById(followingId)) {
            throw new MemberNotFoundException();
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
                .orElseThrow(FollowNotFoundException::new);
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

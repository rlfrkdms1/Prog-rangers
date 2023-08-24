package com.prograngers.backend.service;

import com.prograngers.backend.dto.member.response.MemberProfileResponse;
import com.prograngers.backend.dto.request.UpdateMemberAccountInfoRequest;
import com.prograngers.backend.dto.response.member.MemberAccountInfoResponse;
import com.prograngers.backend.entity.Badge;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.exception.badrequest.BlankNicknameException;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.exception.unauthorization.AlreadyExistNicknameException;
import com.prograngers.backend.exception.unauthorization.IncorrectPasswordException;
import com.prograngers.backend.repository.badge.BadgeRepository;
import com.prograngers.backend.repository.follow.FollowRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;
    private final BadgeRepository badgeRepository;

    private final SolutionRepository solutionRepository;

    private final FollowRepository followRepository;

    public MemberAccountInfoResponse getMemberAccount(Long memberId){
        return MemberAccountInfoResponse.from(findById(memberId));
    }

    public Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }


    public void updateMemberAccountInfo(Long memberId, UpdateMemberAccountInfoRequest updateMemberAccountInfoRequest) {
        Member member = findById(memberId);
        validMemberAccountInfo(updateMemberAccountInfoRequest, member);
        member.update(updateMemberAccountInfoRequest.toMember());
    }

    private void validMemberAccountInfo(UpdateMemberAccountInfoRequest updateMemberAccountInfoRequest, Member member) {
        String nickname = updateMemberAccountInfoRequest.getNickname();
        if(nickname !=null){
            validNicknameBlank(nickname);
            validNicknameDuplication(nickname);
        }
        if(updateMemberAccountInfoRequest.getOldPassword()!=null){
            validCorrectPassword(updateMemberAccountInfoRequest, member);
        }
    }

    private void validCorrectPassword(UpdateMemberAccountInfoRequest updateMemberAccountInfoRequest, Member member) {
        if(member.getPassword().equals(updateMemberAccountInfoRequest.getOldPassword()))
            throw new IncorrectPasswordException();
    }
    private void validNicknameBlank(String nickname) {
        if(nickname.isBlank()) throw new BlankNicknameException();
    }

    private void validNicknameDuplication(String nickname) {
        if(memberRepository.findByNickname(nickname).isPresent())
            throw new AlreadyExistNicknameException();
    }

    public MemberProfileResponse getMemberProfile(Long memberId) {
        Member member = findById(memberId);
        List<Badge> badges = badgeRepository.findAllByMember(member);
        List<Solution> solutions = solutionRepository.findProfileSolutions(memberId);
        Long follow = followRepository.getFollow(member);
        Long following = followRepository.getFollowing(member);

        MemberProfileResponse.from(member,badges,solutions,follow,following);
        return null;
    }
}

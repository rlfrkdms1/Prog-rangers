package com.prograngers.backend.service;

import com.prograngers.backend.dto.member.response.ShowMemberAccountResponse;
import com.prograngers.backend.dto.member.response.ShowMemberProfileResponse;
import com.prograngers.backend.dto.member.request.UpdateMemberAccountRequest;
import com.prograngers.backend.dto.member.response.ShowBasicMemberAccountResponse;
import com.prograngers.backend.dto.member.response.ShowSocialMemberAccountResponse;
import com.prograngers.backend.entity.badge.Badge;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.member.MemberType;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.badrequest.AlreadyDeletedMemberException;
import com.prograngers.backend.exception.badrequest.BlankNicknameException;
import com.prograngers.backend.exception.badrequest.NotExistOldPasswordException;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.exception.unauthorization.AlreadyExistNicknameException;
import com.prograngers.backend.exception.unauthorization.IncorrectPasswordException;
import com.prograngers.backend.exception.unauthorization.QuitMemberException;
import com.prograngers.backend.repository.badge.BadgeRepository;
import com.prograngers.backend.repository.follow.FollowRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private static final Long PROFILE_LAST_PAGE_CURSOR = -1L;
    private static int PROFILE_SIZE_PER_SCROLL = 3;

    private final MemberRepository memberRepository;
    private final BadgeRepository badgeRepository;
    private final SolutionRepository solutionRepository;
    private final FollowRepository followRepository;

    public ShowMemberAccountResponse getMemberAccount(Long memberId) {
        Member member = findById(memberId);
        if (member.getType() == MemberType.BASIC) {
            return ShowBasicMemberAccountResponse.from(member);
        }
        return ShowSocialMemberAccountResponse.from(member);
    }

    private Member findById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    @Transactional
    public void updateMemberAccount(Long memberId, UpdateMemberAccountRequest updateMemberAccountRequest) {
        Member member = findById(memberId);
        validMemberAccount(updateMemberAccountRequest, member);
        member.update(updateMemberAccountRequest.toMember());
    }

    private void validMemberAccount(UpdateMemberAccountRequest updateMemberAccountRequest, Member member) {
        String nickname = updateMemberAccountRequest.getNickname();

        if (nickname != null) {
            validNicknameBlank(nickname);
            validNicknameDuplication(nickname);
        }

        if (updateMemberAccountRequest.getNewPassword() != null) {
            validExistOldPassword(updateMemberAccountRequest);
            validCorrectPassword(updateMemberAccountRequest, member);
        }

    }

    private void validExistOldPassword(UpdateMemberAccountRequest updateMemberAccountRequest) {
        if(updateMemberAccountRequest.getOldPassword()==null){
            throw new NotExistOldPasswordException();
        }
    }

    private void validCorrectPassword(UpdateMemberAccountRequest updateMemberAccountRequest, Member member) {
        if(member.getPassword().equals(updateMemberAccountRequest.getOldPassword()))
            throw new IncorrectPasswordException();
    }

    private void validNicknameBlank(String nickname) {
        if (nickname.isBlank()) {
            throw new BlankNicknameException();
        }
    }

    private void validNicknameDuplication(String nickname) {
        if (memberRepository.findByNickname(nickname).isPresent()) {
            throw new AlreadyExistNicknameException();
        }
    }

    public ShowMemberProfileResponse getMemberProfile(String memberNickname, Long page) {
        Member member = findByNickname(memberNickname);
        validQuitMember(member);
        List<Badge> badges = badgeRepository.findAllByMember(member);
        List<Solution> solutions = solutionRepository.findProfileSolutions(member.getId(), page);
        Long followCount = followRepository.getFollowCount(member);
        Long followingCount = followRepository.getFollowingCount(member);
        Long cursor = setCursor(solutions);

        return ShowMemberProfileResponse.from(member, badges, solutions, followCount, followingCount, cursor);
    }

    private Long setCursor(List<Solution> profileSolutions) {
        Long cursor = PROFILE_LAST_PAGE_CURSOR;
        if (profileSolutions.size() >= PROFILE_SIZE_PER_SCROLL) {
            cursor = profileSolutions.get(PROFILE_SIZE_PER_SCROLL - 1).getId();
            profileSolutions.remove(PROFILE_SIZE_PER_SCROLL - 1);
        }
        return cursor;
    }

    private void validQuitMember(Member member) {
        if (!member.isUsable()) {
            throw new QuitMemberException();
        }
    }

    private Member findByNickname(String memberNickname) {
        return memberRepository.findByNickname(memberNickname).orElseThrow(MemberNotFoundException::new);
    }

    @Transactional
    public void delete(Long memberId) {
        Member member = findById(memberId);
        validAlreadyDeleted(member);
        member.delete();
    }

    private void validAlreadyDeleted(Member member) {
        if (!member.isUsable()) {
            throw new AlreadyDeletedMemberException();
        }
    }
}

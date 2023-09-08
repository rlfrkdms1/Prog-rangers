package com.prograngers.backend.service;

import com.prograngers.backend.dto.response.notification.NotificationResponse;
import com.prograngers.backend.entity.Notification;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.repository.NotificationRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DashBoardService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;

    public void createDashBoard(Long memberId) {
        Member member = findMemberById(memberId);
        List<Notification> notifications = notificationRepository.findTop9ByReceiverOrderByCreatedAtDesc(member);

    }

    private Member findMemberById(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }
}

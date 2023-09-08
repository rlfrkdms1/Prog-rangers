package com.prograngers.backend.service;

import com.prograngers.backend.dto.response.dashboard.MyRecentSolutionInfo;
import com.prograngers.backend.dto.response.dashboard.NotificationInfo;
import com.prograngers.backend.dto.response.notification.NotificationResponse;
import com.prograngers.backend.entity.Notification;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.repository.NotificationRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DashBoardService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;
    private final SolutionRepository solutionRepository;


    public void createDashBoard(Long memberId) {
        Member member = findMemberById(memberId);
        //알림
        List<Notification> notifications = notificationRepository.findTop9ByReceiverOrderByCreatedAtDesc(member);
        List<NotificationInfo> notificationInfoList = notifications.stream().map(notification -> NotificationInfo.of(notification, notification.getSolution())).collect(Collectors.toList());
        //최근 풀이
        List<Solution> solutions = solutionRepository.findTop3ByMemberOrderByCreatedDateDesc(member);
        List<MyRecentSolutionInfo> myRecentSolutionInfos = solutions.stream().map(solution -> MyRecentSolutionInfo.of(solution, solution.getProblem())).collect(Collectors.toList());

    }

    private Member findMemberById(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }
}

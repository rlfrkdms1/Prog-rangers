package com.prograngers.backend.service;

import com.prograngers.backend.dto.response.dashboard.IsDayOfStudy;
import com.prograngers.backend.dto.response.dashboard.NotificationInfo;
import com.prograngers.backend.dto.response.dashboard.ShowDashBoardResponse;
import com.prograngers.backend.dto.response.dashboard.SolutionInfo;
import com.prograngers.backend.entity.Notification;
import com.prograngers.backend.entity.badge.Badge;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.repository.NotificationRepository;
import com.prograngers.backend.repository.badge.BadgeRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.review.ReviewRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DashBoardService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;
    private final SolutionRepository solutionRepository;
    private final BadgeRepository badgeRepository;
    private final ReviewRepository reviewRepository;


    public ShowDashBoardResponse createDashBoard(Long memberId, YearMonth date) {
        if (date == null) date = YearMonth.now();
        Member member = findMemberById(memberId);

        List<NotificationInfo> notificationInfos = getNotificationInfos(member);
        List<SolutionInfo> myRecentSolutionInfos = getMyRecentSolutionInfos(member);
        List<String> badgeInfos = getBadgeInfos(member);
        List<IsDayOfStudy> monthlyStudyCalendar = getMonthlyStudyCalendar(memberId, date);
        List<SolutionInfo> followingRecentSolutionInfos = getFollowingRecentSolutionInfos(memberId);

        return ShowDashBoardResponse.of(monthlyStudyCalendar, notificationInfos, myRecentSolutionInfos, badgeInfos, followingRecentSolutionInfos);
    }

    private List<NotificationInfo> getNotificationInfos(Member member) {
        List<Notification> notifications = notificationRepository.findTop9ByReceiverOrderByCreatedAtDesc(member);
        List<NotificationInfo> notificationInfoList = notifications.stream().map(notification -> NotificationInfo.of(notification, notification.getSolution())).collect(Collectors.toList());
        return notificationInfoList;
    }

    private List<SolutionInfo> getMyRecentSolutionInfos(Member member) {
        List<Solution> myRecentSolutions = solutionRepository.findTop3ByMemberOrderByCreatedAtDesc(member);
        List<SolutionInfo> myRecentSolutionInfos = myRecentSolutions.stream().map(solution -> SolutionInfo.of(solution, solution.getProblem())).collect(Collectors.toList());
        return myRecentSolutionInfos;
    }

    private List<String> getBadgeInfos(Member member) {
        List<Badge> badges = badgeRepository.findAllByMember(member);
        List<String> badgeInfos = badges.stream().map(badge -> badge.getBadgeType().name()).collect(Collectors.toList());
        return badgeInfos;
    }

    private List<SolutionInfo> getFollowingRecentSolutionInfos(Long memberId) {
        List<Solution> followingsRecentSolutions = solutionRepository.findFollowingsRecentSolutions(memberId);
        List<SolutionInfo> followingRecentSolutionInfos = followingsRecentSolutions.stream().map(solution -> SolutionInfo.of(solution, solution.getProblem())).collect(Collectors.toList());
        return followingRecentSolutionInfos;
    }

    private List<IsDayOfStudy> getMonthlyStudyCalendar(Long memberId, YearMonth date) {
        int month = date.getMonthValue();
        List<Integer> monthlyStudy = getMonthlyStudy(memberId, month);
        Map<Integer, Boolean> monthlyStudyMap = IntStream.rangeClosed(1, YearMonth.of(date.getYear(), month).lengthOfMonth()).boxed().collect(Collectors.toMap(Function.identity(), i -> Boolean.FALSE));
        monthlyStudy.stream().forEach(i -> monthlyStudyMap.put(i, true));
        return monthlyStudyMap.entrySet().stream().map(entry -> new IsDayOfStudy(entry.getKey(), entry.getValue())).collect(Collectors.toList());
    }

    private List<Integer> getMonthlyStudy(Long memberId, int month) {
        List<Integer> monthlySolutions = solutionRepository.findAllByMonth(memberId, month);
        List<Integer> monthlyReviews = reviewRepository.findAllByMonth(memberId, month);
        return Stream.concat(monthlySolutions.stream(), monthlyReviews.stream()).distinct().collect(Collectors.toList());
    }

    private Member findMemberById(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }
}

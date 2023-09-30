package com.prograngers.backend.service;

import com.prograngers.backend.dto.dashboard.response.IsDayOfStudyResponse;
import com.prograngers.backend.dto.dashboard.response.NotificationWithSolutionResponse;
import com.prograngers.backend.dto.dashboard.response.ShowDashBoardResponse;
import com.prograngers.backend.dto.dashboard.response.SolutionWithProblemResponse;
import com.prograngers.backend.entity.Notification;
import com.prograngers.backend.entity.badge.Badge;
import com.prograngers.backend.entity.dashboard.MonthConstant;
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
import java.time.LocalDate;
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
<<<<<<< HEAD
        //알림
        List<Notification> notifications = notificationRepository.findTop9ByReceiverOrderByCreatedAtDesc(member);
        List<NotificationInfo> notificationInfoList = notifications.stream().map(notification -> NotificationInfo.of(notification, notification.getSolution())).collect(Collectors.toList());
        //최근 풀이
        List<Solution> myRecentSolutions = solutionRepository.findTop3ByMemberOrderByCreatedAtDesc(member);
        List<SolutionInfo> myRecentSolutionInfos = myRecentSolutions.stream().map(solution -> SolutionInfo.of(solution, solution.getProblem())).collect(Collectors.toList());
        //뱃지 찾기
        List<Badge> badges = badgeRepository.findAllByMember(member);
        List<String> badgeInfos = badges.stream().map(badge -> badge.getBadgeType().name()).collect(Collectors.toList());
        //잔디밭
        List<IsDayOfStudy> monthlyStudyCalendar = getMonthlyStudyCalendar(memberId, MonthConstant.getMonthConstant(date.getMonthValue()), date.getYear());
        //팔로우의 최근 풀이
        List<Solution> followingsRecentSolutions = solutionRepository.findFollowingsRecentSolutions(memberId);
        List<SolutionInfo> followingRecentSolutionInfos = followingsRecentSolutions.stream().map(solution -> SolutionInfo.of(solution, solution.getProblem())).collect(Collectors.toList());

        return ShowDashBoardResponse.of(monthlyStudyCalendar, notificationInfoList, myRecentSolutionInfos, badgeInfos, followingRecentSolutionInfos);
    }

    private List<IsDayOfStudy> getMonthlyStudyCalendar(Long memberId, MonthConstant month, int year) {
        List<Integer> monthlySolutions = solutionRepository.findAllByMonth(memberId, month.getMonth());
        List<Integer> monthlyReviews = reviewRepository.findAllByMonth(memberId, month.getMonth());
        List<Integer> monthlyStudy = Stream.concat(monthlySolutions.stream(), monthlyReviews.stream()).distinct().collect(Collectors.toList());
        Map<Integer, Boolean> monthlyStudyMap = IntStream.rangeClosed(1, month.getLastDayOfMonth(year)).boxed().collect(Collectors.toMap(Function.identity(), i -> Boolean.FALSE));
        monthlyStudy.stream().forEach(i -> monthlyStudyMap.put(i, true));
        List<IsDayOfStudy> monthlyStudyCalendar = monthlyStudyMap.entrySet().stream().map(entry -> new IsDayOfStudy(entry.getKey(), entry.getValue())).collect(Collectors.toList());
        return monthlyStudyCalendar;
=======

        List<NotificationWithSolutionResponse> notifications = getNotifications(member);
        List<SolutionWithProblemResponse> myRecentSolutions = getMyRecentSolutions(member);
        List<String> badges = getBadges(member);
        List<IsDayOfStudyResponse> monthlyStudyCalendar = getMonthlyStudyCalendar(memberId, date);
        List<SolutionWithProblemResponse> followingRecentSolutions = getFollowingRecentSolutions(memberId);

        return ShowDashBoardResponse.of(monthlyStudyCalendar, notifications, myRecentSolutions, badges, followingRecentSolutions);
    }

    private List<NotificationWithSolutionResponse> getNotifications(Member member) {
        List<Notification> notifications = notificationRepository.findTop9ByReceiverOrderByCreatedAtDesc(member);
        return notifications.stream().map(notification -> NotificationWithSolutionResponse.of(notification, notification.getSolution())).collect(Collectors.toList());
    }

    private List<SolutionWithProblemResponse> getMyRecentSolutions(Member member) {
        List<Solution> myRecentSolutions = solutionRepository.findTop3ByMemberOrderByCreatedAtDesc(member);
        return myRecentSolutions.stream().map(solution -> SolutionWithProblemResponse.of(solution, solution.getProblem())).collect(Collectors.toList());
    }

    private List<String> getBadges(Member member) {
        List<Badge> badges = badgeRepository.findAllByMember(member);
        return badges.stream().map(badge -> badge.getBadgeType().name()).collect(Collectors.toList());
    }

    private List<SolutionWithProblemResponse> getFollowingRecentSolutions(Long memberId) {
        List<Solution> followingsRecentSolutions = solutionRepository.findFollowingsRecentSolutions(memberId);
        return followingsRecentSolutions.stream().map(solution -> SolutionWithProblemResponse.of(solution, solution.getProblem())).collect(Collectors.toList());
    }

    private List<IsDayOfStudyResponse> getMonthlyStudyCalendar(Long memberId, YearMonth date) {
        int month = date.getMonthValue();
        List<Integer> monthlyStudy = getMonthlyStudy(memberId, month);
        Map<Integer, Boolean> monthlyStudyMap = IntStream.rangeClosed(1, YearMonth.of(date.getYear(), month).lengthOfMonth()).boxed().collect(Collectors.toMap(Function.identity(), i -> Boolean.FALSE));
        monthlyStudy.stream().forEach(i -> monthlyStudyMap.put(i, true));
        return monthlyStudyMap.entrySet().stream().map(entry -> new IsDayOfStudyResponse(entry.getKey(), entry.getValue())).collect(Collectors.toList());
    }

    private List<Integer> getMonthlyStudy(Long memberId, int month) {
        List<Integer> monthlySolutions = solutionRepository.findAllByMonth(memberId, month);
        List<Integer> monthlyReviews = reviewRepository.findAllByMonth(memberId, month);
        return Stream.concat(monthlySolutions.stream(), monthlyReviews.stream()).distinct().collect(Collectors.toList());
>>>>>>> 1e68fe7e332e0378fce8667f04f28cff021631e1
    }

    private Member findMemberById(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }
}

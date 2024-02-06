package com.prograngers.backend.service;

import static com.prograngers.backend.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;

import com.prograngers.backend.dto.dashboard.response.IsDayOfStudyResponse;
import com.prograngers.backend.dto.dashboard.response.NotificationWithSolutionResponse;
import com.prograngers.backend.dto.dashboard.response.ShowCalendarResponse;
import com.prograngers.backend.dto.dashboard.response.ShowDashBoardResponse;
import com.prograngers.backend.dto.dashboard.response.SolutionWithProblemResponse;
import com.prograngers.backend.entity.Notification;
import com.prograngers.backend.entity.badge.Badge;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.NotFoundException;
import com.prograngers.backend.repository.badge.BadgeRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.notification.NotificationRepository;
import com.prograngers.backend.repository.review.ReviewRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import java.time.YearMonth;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class DashBoardService {

    private final NotificationRepository notificationRepository;
    private final MemberRepository memberRepository;
    private final SolutionRepository solutionRepository;
    private final BadgeRepository badgeRepository;
    private final ReviewRepository reviewRepository;

    public static final int DASHBOARD_NOTIFICATION_LIMIT = 9;
    private static final int DASHBOARD_RECENT_SOLUTION_LIMIT = 3;
    private static final int DASHBOARD_RECENT_FOLLOWINGS_SOLUTION_LIMIT = 3;

    @Transactional
    public ShowDashBoardResponse getDashboard(Long memberId, YearMonth date) {
        if (date == null) {
            date = YearMonth.now();
        }
        Member member = findMemberById(memberId);

        List<NotificationWithSolutionResponse> notifications = getNotifications(memberId);
        List<SolutionWithProblemResponse> myRecentSolutions = getMyRecentSolutions(memberId);
        List<String> badges = getBadges(member);
        List<IsDayOfStudyResponse> monthlyStudyCalendar = getMonthlyStudyCalendar(memberId, date);
        List<SolutionWithProblemResponse> followingRecentSolutions = getFollowingRecentSolutions(memberId);

        return ShowDashBoardResponse.of(monthlyStudyCalendar, notifications, myRecentSolutions, badges,
                followingRecentSolutions);
    }

    private List<NotificationWithSolutionResponse> getNotifications(Long memberId) {
        List<Notification> notifications = notificationRepository.findByMemberIdAndLimit(memberId,
                DASHBOARD_NOTIFICATION_LIMIT);
        List<NotificationWithSolutionResponse> response = notifications.stream()
                .map(notification -> NotificationWithSolutionResponse.of(notification, notification.getSolution()))
                .collect(Collectors.toList());
        notifications.stream().forEach(Notification::read);
        return response;
    }

    private List<SolutionWithProblemResponse> getMyRecentSolutions(Long memberId) {
        List<Solution> myRecentSolutions = solutionRepository.findMyRecentSolutions(memberId,
                DASHBOARD_RECENT_SOLUTION_LIMIT);
        return myRecentSolutions.stream().map(SolutionWithProblemResponse::from).collect(Collectors.toList());
    }

    private List<String> getBadges(Member member) {
        List<Badge> badges = badgeRepository.findAllByMember(member);
        return badges.stream().map(badge -> badge.getBadgeType().name()).collect(Collectors.toList());
    }

    private List<SolutionWithProblemResponse> getFollowingRecentSolutions(Long memberId) {
        List<Solution> followingsRecentSolutions = solutionRepository.findFollowingsRecentSolutions(memberId,
                DASHBOARD_RECENT_FOLLOWINGS_SOLUTION_LIMIT);
        return followingsRecentSolutions.stream().map(SolutionWithProblemResponse::from).collect(Collectors.toList());
    }

    private List<IsDayOfStudyResponse> getMonthlyStudyCalendar(Long memberId, YearMonth date) {
        int month = date.getMonthValue();
        List<Integer> monthlyStudy = getMonthlyStudy(memberId, month);
        Map<Integer, Boolean> monthlyStudyMap = IntStream.rangeClosed(1,
                        YearMonth.of(date.getYear(), month).lengthOfMonth()).boxed()
                .collect(Collectors.toMap(Function.identity(), i -> Boolean.FALSE));
        monthlyStudy.stream().forEach(i -> monthlyStudyMap.put(i, true));
        return monthlyStudyMap.entrySet().stream()
                .map(entry -> new IsDayOfStudyResponse(entry.getKey(), entry.getValue())).collect(Collectors.toList());
    }

    private List<Integer> getMonthlyStudy(Long memberId, int month) {
        List<Integer> monthlySolutions = solutionRepository.findAllByMonth(memberId, month);
        List<Integer> monthlyReviews = reviewRepository.findAllByMonth(memberId, month);
        return Stream.concat(monthlySolutions.stream(), monthlyReviews.stream()).distinct()
                .collect(Collectors.toList());
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND));
    }

    public ShowCalendarResponse getCalendar(Long memberId, YearMonth date) {
        return new ShowCalendarResponse(getMonthlyStudyCalendar(memberId, date));
    }
}

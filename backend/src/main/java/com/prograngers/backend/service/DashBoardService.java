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
        List<IsDayOfStudy> monthlyStudyCalendar = getMonthlyStudyCalendar(memberId, date);
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
    }

    private Member findMemberById(Long memberId){
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }
}

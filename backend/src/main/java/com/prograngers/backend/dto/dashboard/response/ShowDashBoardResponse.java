package com.prograngers.backend.dto.dashboard.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShowDashBoardResponse {

    private List<IsDayOfStudyResponse> monthlyStudyCalendar;
    private List<NotificationWithSolutionResponse> notifications;
    private List<SolutionWithProblemResponse> recentProblems;
    private List<String> badges;
    private List<SolutionWithProblemResponse> followingRecentSolutions;

    @Builder
    public ShowDashBoardResponse(List<IsDayOfStudyResponse> monthlyStudyCalendar, List<NotificationWithSolutionResponse> notifications, List<SolutionWithProblemResponse> recentProblems, List<String> badges, List<SolutionWithProblemResponse> followingRecentSolutions) {
        this.monthlyStudyCalendar = monthlyStudyCalendar;
        this.notifications = notifications;
        this.recentProblems = recentProblems;
        this.badges = badges;
        this.followingRecentSolutions = followingRecentSolutions;
    }

    public static ShowDashBoardResponse of(List<IsDayOfStudyResponse> monthlyStudyCalendar,
                                           List<NotificationWithSolutionResponse> notifications,
                                           List<SolutionWithProblemResponse> recentProblems,
                                           List<String> badges,
                                           List<SolutionWithProblemResponse> followingRecentSolutions){
        return ShowDashBoardResponse.builder()
                .monthlyStudyCalendar(monthlyStudyCalendar)
                .notifications(notifications)
                .recentProblems(recentProblems)
                .badges(badges)
                .followingRecentSolutions(followingRecentSolutions)
                .build();
    }
}

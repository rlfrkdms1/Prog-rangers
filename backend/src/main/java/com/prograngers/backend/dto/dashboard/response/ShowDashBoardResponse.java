package com.prograngers.backend.dto.dashboard.response;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ShowDashBoardResponse {

    private List<IsDayOfStudy> monthlyStudyCalendar;
    private List<NotificationInfo> notificationInfoList;
    private List<SolutionInfo> myRecentSolutionInfos;
    private List<String> badgeInfos;
    private List<SolutionInfo> followingRecentSolutionInfos;

    @Builder
    public ShowDashBoardResponse(List<IsDayOfStudy> monthlyStudyCalendar, List<NotificationInfo> notificationInfoList, List<SolutionInfo> myRecentSolutionInfos, List<String> badgeInfos, List<SolutionInfo> followingRecentSolutionInfos) {
        this.monthlyStudyCalendar = monthlyStudyCalendar;
        this.notificationInfoList = notificationInfoList;
        this.myRecentSolutionInfos = myRecentSolutionInfos;
        this.badgeInfos = badgeInfos;
        this.followingRecentSolutionInfos = followingRecentSolutionInfos;
    }

    public static ShowDashBoardResponse of(List<IsDayOfStudy> monthlyStudyCalendar,
                                           List<NotificationInfo> notificationInfoList,
                                           List<SolutionInfo> myRecentSolutionInfos,
                                           List<String> badgeInfos,
                                           List<SolutionInfo> followingRecentSolutionInfos){
        return ShowDashBoardResponse.builder()
                .monthlyStudyCalendar(monthlyStudyCalendar)
                .notificationInfoList(notificationInfoList)
                .myRecentSolutionInfos(myRecentSolutionInfos)
                .badgeInfos(badgeInfos)
                .followingRecentSolutionInfos(followingRecentSolutionInfos)
                .build();
    }
}

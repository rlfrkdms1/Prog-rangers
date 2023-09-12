package com.prograngers.backend.dto.response.dashboard;

import com.prograngers.backend.service.DashBoardService;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
@Builder
public class ShowDashBoardResponse {

    private List<IsDayOfStudy> monthlyStudyCalendar;
    private List<NotificationInfo> notificationInfoList;
    private List<SolutionInfo> myRecentSolutionInfos;
    private List<String> badgeInfos;
    private List<SolutionInfo> followingRecentSolutionInfos;


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

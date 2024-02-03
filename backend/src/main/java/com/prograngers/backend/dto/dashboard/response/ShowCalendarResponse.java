package com.prograngers.backend.dto.dashboard.response;

import java.util.List;

public class ShowCalendarResponse {
    private List<IsDayOfStudyResponse> calendar;

    public ShowCalendarResponse(List<IsDayOfStudyResponse> calendar) {
        this.calendar = calendar;
    }
}

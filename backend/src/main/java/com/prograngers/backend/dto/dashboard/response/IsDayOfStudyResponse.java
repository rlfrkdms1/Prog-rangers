package com.prograngers.backend.dto.dashboard.response;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class IsDayOfStudyResponse {
    private int day;
    private boolean studied;
}

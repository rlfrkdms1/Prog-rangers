package com.prograngers.backend.dto.response.dashboard;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IsDayOfStudy {
    private int day;
    private boolean isStudied;
}

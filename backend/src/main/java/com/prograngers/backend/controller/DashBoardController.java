package com.prograngers.backend.controller;

import com.prograngers.backend.controller.auth.LoggedInMember;
import com.prograngers.backend.controller.auth.Login;
import com.prograngers.backend.dto.dashboard.response.ShowDashBoardResponse;
import com.prograngers.backend.service.DashBoardService;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.YearMonth;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class DashBoardController {

    private final DashBoardService dashBoardService;

    @Login
    @GetMapping("/mypage/dashboard")
    public ShowDashBoardResponse show(@LoggedInMember Long memberId,
                                      @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM") YearMonth date){
        return dashBoardService.createDashBoard(memberId, date);
    }
}

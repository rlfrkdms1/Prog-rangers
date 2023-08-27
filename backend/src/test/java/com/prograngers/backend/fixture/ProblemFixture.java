package com.prograngers.backend.fixture;

import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.JudgeConstant;
import lombok.AllArgsConstructor;

import java.util.List;

import static com.prograngers.backend.entity.constants.JudgeConstant.백준;
import static com.prograngers.backend.entity.constants.JudgeConstant.프로그래머스;

@AllArgsConstructor
public enum ProblemFixture {

    백준_문제("백준문제", "https://www.acmicpc.net/problem/2557", 백준),
    프로그래머스_문제("프로그래머스 문제", "https://school.programmers.co.kr/learn/courses/30/lessons/164673", 프로그래머스);

    private final String title;
    private final String link;
    private final JudgeConstant ojName;

    public Problem.ProblemBuilder 기본_정보_빌더_생성() {
        return Problem.builder()
                .title(title)
                .link(link)
                .ojName(ojName);
    }

    public Problem 기본_정보_문제_생성(){
        return 기본_정보_빌더_생성()
                .build();
    }
}
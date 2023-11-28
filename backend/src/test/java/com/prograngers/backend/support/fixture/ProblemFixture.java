package com.prograngers.backend.support.fixture;

import static com.prograngers.backend.entity.problem.JudgeConstant.백준;
import static com.prograngers.backend.entity.problem.JudgeConstant.프로그래머스;

import com.prograngers.backend.entity.problem.JudgeConstant;
import com.prograngers.backend.entity.problem.Problem;
import java.util.ArrayList;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum ProblemFixture {

    백준_문제("백준 문제", "https://www.acmicpc.net/problem/2557", 백준),
    프로그래머스_문제("프로그래머스 문제", "https://school.programmers.co.kr/learn/courses/30/lessons/164673", 프로그래머스);

    private final String title;
    private final String link;
    private final JudgeConstant ojName;

    public Problem.ProblemBuilder 기본_정보_빌더_생성() {
        return Problem.builder()
                .title(title)
                .link(link)
                .ojName(ojName)
                .solutions(new ArrayList<>());
    }

    public Problem 기본_정보_생성() {
        return 기본_정보_빌더_생성()
                .build();
    }

    public Problem 아이디_지정_생성(Long id) {
        return 기본_정보_빌더_생성()
                .id(id)
                .build();
    }
}
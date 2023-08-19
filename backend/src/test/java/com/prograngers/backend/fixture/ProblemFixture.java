package com.prograngers.backend.fixture;

import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.constants.JudgeConstant;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;

import static com.prograngers.backend.entity.constants.JudgeConstant.백준;

@AllArgsConstructor
public enum ProblemFixture {

    문제1(1L,"문제제목1","https://www.acmicpc.net/problem/1000",LocalDate.now(),백준),
    문제2(2L,"문제제목2","https://www.acmicpc.net/problem/1001",LocalDate.now().plusDays(1),백준),
    문제3(3L,"문제제목3","https://www.acmicpc.net/problem/1002",LocalDate.now().plusDays(2),백준);
    private final Long id;
    private final String title;

    private final String link;

    private final LocalDate date;

    private final JudgeConstant ojName;



    public Problem getProblem() {
        return Problem.builder()
                .id(this.id)
                .title(this.title)
                .link(this.link)
                .date(this.date)
                .ojName(this.ojName)
                .solutions(new ArrayList<>())
                .build();
    }
    public Problem 아이디_값_지정_문제_생성(Long id) {
        return Problem.builder()
                .id(id)
                .title(this.title)
                .link(this.link)
                .date(this.date)
                .ojName(this.ojName)
                .solutions(new ArrayList<>())
                .build();
    }

    public Problem 아이디_값_지정_문제_생성() {
        return Problem.builder()
                .id(null)
                .title(this.title)
                .link(this.link)
                .date(this.date)
                .ojName(this.ojName)
                .solutions(new ArrayList<>())
                .build();
    }

}

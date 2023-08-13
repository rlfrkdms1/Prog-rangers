package com.prograngers.backend.fixture;

import com.prograngers.backend.entity.Likes;
import com.prograngers.backend.entity.Member;
import com.prograngers.backend.entity.Problem;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.constants.JudgeConstant;
import com.prograngers.backend.entity.constants.LevelConstant;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.prograngers.backend.entity.constants.JudgeConstant.백준;

@AllArgsConstructor
public enum ProblemFixture {

    문제1(1L,"문제제목1","www.problem1.com",LocalDate.now(),백준),
    문제2(2L,"문제제목2","www.problem2.com",LocalDate.now().plusDays(1),백준),
    문제3(3L,"문제제목3","www.problem3.com",LocalDate.now().plusDays(2),백준);
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
}

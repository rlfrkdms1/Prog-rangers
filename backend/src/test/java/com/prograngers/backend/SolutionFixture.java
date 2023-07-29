package com.prograngers.backend;

import com.prograngers.backend.entity.*;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
public enum SolutionFixture {
    SOLUTION(
            "풀이제목",
            true,
            "코드",
            "설명",
            0,
            LocalDate.now(),
            new Algorithm(null,Algorithms.BFS),
            new DataStructure(null, DataStructures.ARRAY),
            Levels.ONE
            );

    private final String title;

    private final boolean isPublic;

    private final String code;

    private final String description;

    private final Integer scraps;

    private final LocalDate date;

    private final Algorithm algorithm;

    private final DataStructure dataStructure;

    private final Levels level;

    public Solution getSolution(Long id, Problem problem, Member member, Solution scrapSolution){
        Solution build = Solution.builder()
                .id(id)
                .problem(problem)
                .member(member)
                .scrapId(scrapSolution)
                .title(this.title)
                .isPublic(this.isPublic)
                .code(this.code)
                .description(this.description)
                .scraps(scraps)
                .date(this.date)
                .algorithm(this.algorithm)
                .dataStructure(this.dataStructure)
                .level(this.level)
                .build();
        return build;
    }
}

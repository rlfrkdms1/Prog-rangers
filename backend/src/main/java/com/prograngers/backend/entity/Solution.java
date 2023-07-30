package com.prograngers.backend.entity;

import com.prograngers.backend.entity.constants.AlgorithmConstant;
import com.prograngers.backend.entity.constants.DataStructureConstant;
import com.prograngers.backend.entity.constants.LevelConstant;
import jakarta.annotation.Nullable;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Solution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "problem_id")
    private Problem problem;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "member_id")
    private Member member;

    private String title;

    private boolean isPublic;

    private String code;

    private String description;

    private Integer scraps;

    @OneToMany(mappedBy = "solution")
    private List<Likes> likes = new ArrayList<>();

    @OneToOne
    @JoinColumn(name = "scrap_id")
    @Nullable
    private Solution scrapId;

    private LocalDate date;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "algorithm_id")
    private Algorithm algorithm;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "dataStructure_id")
    private DataStructure dataStructure;

    @Enumerated(EnumType.STRING)
    private LevelConstant level;

    public void updateProblem(Problem problem) {
        if (problem != null) {
            this.problem = problem;
        }
    }

    public void updateMember(Member member) {
        if (member != null) {
            this.member = member;
        }
    }

    public void updateTitle(String title) {
        if (title != null) {
            this.title = title;
        }
    }

    public void updateIsPublic(boolean isPublic) {
        this.isPublic = isPublic;
    }

    public void updateCode(String code) {
        if (!code.isEmpty()) {
            this.code = code;
        }
    }

    public void updateDescription(String description) {
        if (!code.isEmpty()) {
            this.description = description;
        }
    }


    public void upScraps() {
        this.scraps += 1;
    }

    public void downScraps() {
        this.scraps -= 1;
    }

    public void updateScrapId(Solution solution) {
        if (solution != null) {
            this.scrapId = solution;
        }
    }

    public void updateAlgorithm(AlgorithmConstant algorithm) {
        this.algorithm = Algorithm.builder()
                .name(algorithm)
                .build();
    }

    public void updateDataStructure(DataStructureConstant dataStructure) {
        this.dataStructure = DataStructure.builder()
                .name(dataStructure)
                .build();
    }

    public void updateLevel(LevelConstant level) {
        this.level = level;
    }
}

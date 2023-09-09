package com.prograngers.backend.entity.solution;

import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.member.Member;
import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDateTime;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Solution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.PERSIST, fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private boolean isPublic;

    @Column(nullable = false)
    private String code;

    @Column(nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_id")
    @Nullable
    private Solution scrapSolution;

    @Column(nullable = false, updatable = false)
    @CreatedDate
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private AlgorithmConstant algorithm;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DataStructureConstant dataStructure;

    @Column(nullable = false)
    private Integer level;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private LanguageConstant language;

    private void updateTitle(String title) {
        if (title != null) {
            this.title = title;
        }
    }

    private void updateCode(String code) {
        if (!code.isEmpty()) {
            this.code = code;
        }
    }

    private void updateDescription(String description) {
        if (!code.isEmpty()) {
            this.description = description;
        }
    }

    private void updateLevel(Integer level){
        this.level = level;
    }

    private void updateAlgorithm(AlgorithmConstant algorithm) {
        this.algorithm = algorithm;
    }

    private void updateDataStructure(DataStructureConstant dataStructure) {
        this.dataStructure = dataStructure;
    }

    public void update(String title, AlgorithmConstant algorithm, DataStructureConstant dataStructure, int level, String code, String description){
        updateTitle(title);
        updateAlgorithm(algorithm);
        updateDataStructure(dataStructure);
        updateLevel(level);
        updateCode(code);
        updateDescription(description);
    }

}

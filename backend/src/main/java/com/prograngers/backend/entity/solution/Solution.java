package com.prograngers.backend.entity.solution;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.support.converter.AlgorithmConverter;
import com.prograngers.backend.support.converter.DataStructureConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Solution {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "problem_id", nullable = false)
    private Problem problem;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "is_public", nullable = false)
    private boolean isPublic;

    @Column(name = "code", nullable = false)
    private String code;

    @Column(name = "description", nullable = false)
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "scrap_id")
    private Solution scrapSolution;

    @CreatedDate
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Convert(converter = AlgorithmConverter.class)
    @Column(name = "algorithm")
    private AlgorithmConstant algorithm;

    @Convert(converter = DataStructureConverter.class)
    @Column(name = "data_structure")
    private DataStructureConstant dataStructure;

    @Column(name = "level", nullable = false)
    private Integer level;

    @Enumerated(EnumType.STRING)
    @Column(name = "language", nullable = false)
    private LanguageConstant language;

    private void updateTitle(String title) {
        if (title == null || title.isBlank()) {
            return;
        }
        this.title = title;
    }

    private void updateCode(String code) {
        if (code == null || code.isBlank()) {
            return;
        }
        this.code = code;
    }

    private void updateDescription(String description) {
        if (description == null || description.isBlank()) {
            return;
        }
        this.description = description;
    }

    private void updateLevel(Integer level) {
        if (level != null) {
            this.level = level;
        }
    }

    private void updateAlgorithm(AlgorithmConstant algorithm) {
        if (algorithm != null) {
            this.algorithm = algorithm;
        }
    }

    private void updateDataStructure(DataStructureConstant dataStructure) {
        if (dataStructure != null) {
            this.dataStructure = dataStructure;
        }
    }

    public void update(Solution update) {
        updateTitle(update.getTitle());
        updateAlgorithm(update.getAlgorithm());
        updateDataStructure(update.getDataStructure());
        updateLevel(update.getLevel());
        updateCode(update.getCode());
        updateDescription(update.getDescription());
    }

    public String getAlgorithmName() {
        if (algorithm != null) {
            return algorithm.name();
        }
        return null;
    }

    public String getDataStructureName() {
        if (dataStructure != null) {
            return dataStructure.name();
        }
        return null;
    }

    public boolean isScrapped() {
        return scrapSolution != null;
    }

    /**
     * 풀이의 id와 날짜을 { id : %d, date : %s }의 문자열 형태로 반환합니다 { id : 880, date : 2024-01-24T19:26:01.658721100 } 입니다. 이 형식은
     * 언제든 바뀔 수 있습니다
     */
    @Override
    public String toString() {
        return String.format("{ id : %d, date : %s }", id, createdAt);
    }
}

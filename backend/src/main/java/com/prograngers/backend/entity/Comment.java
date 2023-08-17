package com.prograngers.backend.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
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

import java.time.LocalDate;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "solution_id")
    private Solution solution;
    private String mention;

    private String content;

    private LocalDate date;

    private Long parentId;

    private boolean fixed;

    public void updateMention(String mention) {
        if (mention != null) {
            this.mention = mention;
            this.fixed = true;
        }
    }

    public void updateContent(String content) {
        if (content != null) {
            this.content = content;
            this.fixed = true;
        }
    }

    public void updateSolution(Solution solution) {
        this.solution = solution;
    }
}

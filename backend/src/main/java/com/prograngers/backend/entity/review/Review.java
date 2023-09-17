package com.prograngers.backend.entity.review;

import com.prograngers.backend.entity.comment.CommentStatusConStant;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
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

import java.time.LocalDateTime;

import static com.prograngers.backend.entity.review.ReviewStatusConStant.*;

@Entity
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    private static final String DELETED_CONTENT = "삭제된 리뷰입니다";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "solution_id",nullable = false)
    private Solution solution;

    @Column(nullable = false)
    private Integer codeLineNumber;

    private Long parentId;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false,updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private ReviewStatusConStant status;

    public Review update(String content){
        this.content = content;
        this.status = FIXED;
        return this;
    }

    public void delete() {
        this.content = DELETED_CONTENT;
        this.status=DELETED;
    }
}

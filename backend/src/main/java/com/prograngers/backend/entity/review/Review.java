package com.prograngers.backend.entity.review;

import static com.prograngers.backend.entity.review.ReviewStatusConstant.DELETED;
import static com.prograngers.backend.entity.review.ReviewStatusConstant.FIXED;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
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
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
public class Review {

    private static final String DELETED_CONTENT = "삭제된 리뷰입니다";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "solution_id", nullable = false)
    private Solution solution;

    @Column(nullable = false)
    private Integer codeLineNumber;

    private Long parentId;

    @Column(nullable = false)
    private String content;

    @CreatedDate
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false, name = "status")
    @Enumerated(value = EnumType.STRING)
    private ReviewStatusConstant status;

    public void update(String content) {
        this.content = content;
        this.status = FIXED;
    }

    public void delete() {
        this.content = DELETED_CONTENT;
        this.status = DELETED;
    }
}

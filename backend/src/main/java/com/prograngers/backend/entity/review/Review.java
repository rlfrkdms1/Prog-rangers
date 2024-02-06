package com.prograngers.backend.entity.review;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
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

    public static final String DELETED_CONTENT = "삭제된 리뷰입니다";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id", nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
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

    @Column(nullable = false)
    private boolean updated;

    public void update(String content) {
        if (content == null || content.isBlank()) {
            return;
        }
        this.content = content;
        this.updated = true;
    }

    @Override
    public String toString() {
        return "Review{" +
                "id=" + id +
                ", memberId=" + member.getId() +
                ", solutionId=" + solution.getId() +
                ", codeLineNumber=" + codeLineNumber +
                ", parentId=" + parentId +
                ", content='" + content + '\'' +
                ", createdAt=" + createdAt +
                ", updated=" + updated +
                '}';
    }
}

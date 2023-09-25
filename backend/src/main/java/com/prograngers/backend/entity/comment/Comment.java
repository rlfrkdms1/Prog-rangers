package com.prograngers.backend.entity.comment;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
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

import static com.prograngers.backend.entity.comment.CommentStatusConstant.*;

@Entity
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Comment {


    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id",nullable = false)
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solution_id",nullable = false)
    private Solution solution;

    @Column(nullable = false)
    private String content;

    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    private Long parentId;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private CommentStatusConstant status;

    public void update(String content) {
        if (content != null) {
            this.content = content;
            this.status = FIXED;
        }
    }

    private static final String DELETED_CONTENT = "삭제된 댓글입니다";

    public void delete(){
        this.content = DELETED_CONTENT;
        this.status = DELETED;
    }

}

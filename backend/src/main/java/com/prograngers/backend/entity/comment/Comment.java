package com.prograngers.backend.entity.comment;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
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

import java.time.LocalDateTime;

import static com.prograngers.backend.entity.comment.CommentStatusContant.*;

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
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "solution_id")
    private Solution solution;
    private String mention;

    private String content;

    private LocalDateTime createdDate;

    private Long parentId;

    private CommentStatusContant status;

    public void updateMention(String mention) {
        if (mention != null) {
            this.mention = mention;
            this.status = FIXED;
        }
    }

    public void updateContent(String content) {
        if (content != null) {
            this.content = content;
            this.status = FIXED;
        }
    }

    public void deleteComment(){
        this.status = DELETED;
    }

}

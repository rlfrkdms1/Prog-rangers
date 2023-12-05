package com.prograngers.backend.support.fixture;

import static com.prograngers.backend.entity.comment.Comment.CommentBuilder;
import static com.prograngers.backend.entity.comment.CommentStatusConstant.CREATED;
import static com.prograngers.backend.entity.comment.CommentStatusConstant.DELETED;
import static com.prograngers.backend.entity.comment.CommentStatusConstant.FIXED;

import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.comment.CommentStatusConstant;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CommentFixture {

    생성된_댓글("댓글내용", CREATED),
    수정된_댓글("댓글내용", FIXED),
    삭제된_댓글("댓글내용", DELETED);

    private final String content;
    private final CommentStatusConstant status;

    public CommentBuilder 기본_빌더_생성() {
        return Comment.builder()
                .content(content)
                .status(status);
    }

    public Comment 기본_정보_생성(Member member, Solution solution, LocalDateTime createdDate) {
        return 기본_빌더_생성()
                .member(member)
                .solution(solution)
                .createdAt(createdDate)
                .build();
    }

    public Comment 부모_지정_생성(Long parentId, Long id, Member member, Solution solution, LocalDateTime createdDate) {
        return 기본_빌더_생성()
                .parentId(parentId)
                .id(id)
                .member(member)
                .solution(solution)
                .createdAt(createdDate)
                .build();
    }

    public Comment 아이디_지정_생성(Long id, Member member, Solution solution, LocalDateTime createdDate) {
        return 기본_빌더_생성()
                .id(id)
                .member(member)
                .solution(solution)
                .createdAt(createdDate)
                .build();
    }

}

package com.prograngers.backend.support.fixture;

import static com.prograngers.backend.entity.comment.Comment.CommentBuilder;
import static com.prograngers.backend.entity.comment.Comment.builder;

import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum CommentFixture {
    생성된_댓글("생성된 댓글", false),

    수정된_댓글("수정된 댓글", true);

    private final String content;
    private final boolean updated;

    public CommentBuilder 기본_빌더_생성() {
        return builder()
                .content(content)
                .updated(updated);
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

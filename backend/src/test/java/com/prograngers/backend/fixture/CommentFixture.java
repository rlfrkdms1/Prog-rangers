package com.prograngers.backend.fixture;

import com.prograngers.backend.entity.Comment;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum
CommentFixture {
    댓글1("댓글내용1"),
    댓글2("댓글내용2"),
    댓글3("댓글내용3"),
    삭제된_댓글("삭제된 댓글입니다");
    private final String content;

    public Comment.CommentBuilder 기본_빌더_생성() {
        return Comment.builder()
                .content(this.content);
    }

    public Comment 댓글_생성(Long id, Solution solution, Member member) {
        return 기본_빌더_생성()
                .id(id)
                .solution(solution)
                .member(member)
                .build();
    }

    public Comment 기본_댓글_생성(Long id){
        return 기본_빌더_생성()
                .id(id)
                .build();
    }
}

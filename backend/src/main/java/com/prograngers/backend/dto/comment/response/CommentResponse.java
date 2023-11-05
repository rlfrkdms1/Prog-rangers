package com.prograngers.backend.dto.comment.response;

import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.member.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
public class CommentResponse {

    private String status;
    private String content;
    private AuthorResponse author;

    public static CommentResponse from(Comment comment) {
        Member member = comment.getMember();
        return CommentResponse.builder()
                .status(comment.getStatus().toString())
                .content(comment.getContent())
                .author(AuthorResponse.from(member))
                .build();
    }

}

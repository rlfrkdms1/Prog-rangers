package com.prograngers.backend.dto.comment.response;

import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.member.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentResponse {

    private String status;
    private String content;
    private String authorImageUrl;
    private String authorNickname;

    public static CommentResponse from(Comment comment) {
        Member member = comment.getMember();
        return new CommentResponse(comment.getStatus().name(), comment.getContent(), member.getPhoto(), member.getNickname());
    }

}

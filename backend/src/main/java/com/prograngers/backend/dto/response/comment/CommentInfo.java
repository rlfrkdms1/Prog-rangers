package com.prograngers.backend.dto.response.comment;

import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.member.Member;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentInfo {

    private String content;
    private String authorImageUrl;
    private String authorNickname;

    public static CommentInfo from(Comment comment) {
        Member member = comment.getMember();
        return new CommentInfo(comment.getContent(), member.getPhoto(), member.getNickname());
    }

}

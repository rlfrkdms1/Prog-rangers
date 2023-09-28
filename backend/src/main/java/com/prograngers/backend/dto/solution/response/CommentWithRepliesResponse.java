package com.prograngers.backend.dto.solution.response;


import com.prograngers.backend.entity.comment.Comment;

import com.prograngers.backend.entity.comment.CommentStatusConstant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentWithRepliesResponse {
    String photo;
    Long id;
    String nickname;
    String content;
    CommentStatusConstant status;
    List<CommentWithRepliesResponse> replies;

    boolean mine;

    public static CommentWithRepliesResponse from(Comment comment, ArrayList<CommentWithRepliesResponse> replies, boolean isMine){

        return new CommentWithRepliesResponse(comment.getMember().getPhoto(), comment.getId(), comment.getMember().getNickname(), comment.getContent(), comment.getStatus(), replies,
               isMine);
    }

    public static CommentWithRepliesResponse from(Comment comment, boolean isMine){
        return new CommentWithRepliesResponse(comment.getMember().getPhoto(), comment.getId(), comment.getMember().getNickname(), comment.getContent(), comment.getStatus(), null
        ,isMine);
    }

}

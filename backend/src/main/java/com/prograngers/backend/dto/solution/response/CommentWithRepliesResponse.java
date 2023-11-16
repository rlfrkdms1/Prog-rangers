package com.prograngers.backend.dto.solution.response;


import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.comment.CommentStatusConstant;
import java.util.ArrayList;
import java.util.List;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CommentWithRepliesResponse {
    private String photo;
    private Long id;
    private String nickname;
    private String content;
    private CommentStatusConstant status;
    private List<CommentWithRepliesResponse> replies;
    private boolean mine;

    public static CommentWithRepliesResponse of(Comment comment, ArrayList<CommentWithRepliesResponse> replies,
                                                boolean isMine) {
        return CommentWithRepliesResponse.builder()
                .photo(comment.getMember().getPhoto())
                .id(comment.getId())
                .nickname(comment.getMember().getNickname())
                .content(comment.getContent())
                .status(comment.getStatus())
                .replies(replies)
                .mine(isMine)
                .build();
    }

    public static CommentWithRepliesResponse of(Comment comment, boolean isMine) {
        return CommentWithRepliesResponse.builder()
                .photo(comment.getMember().getPhoto())
                .id(comment.getId())
                .nickname(comment.getMember().getNickname())
                .content(comment.getContent())
                .status(comment.getStatus())
                .mine(isMine)
                .build();
    }
}

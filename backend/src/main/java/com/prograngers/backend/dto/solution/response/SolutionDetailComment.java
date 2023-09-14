package com.prograngers.backend.dto.solution.response;


import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.comment.CommentStatusConStant;
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
public class SolutionDetailComment {
    String photo;
    Long id;
    String nickname;
    String content;
    CommentStatusConStant status;
    List<SolutionDetailComment> replies;

    boolean mine;

    public static SolutionDetailComment from(Comment comment, ArrayList<SolutionDetailComment> replies,Long memberId){

        return new SolutionDetailComment(comment.getMember().getPhoto(), comment.getId(), comment.getMember().getNickname(), comment.getContent(), comment.getStatus(), replies,
                checkCommentIsMine(comment.getMember().getId(), memberId));
    }

    public static SolutionDetailComment from(Comment comment, Long memberId){
        return new SolutionDetailComment(comment.getMember().getPhoto(), comment.getId(), comment.getMember().getNickname(), comment.getContent(), comment.getStatus(), null
        ,checkCommentIsMine(comment.getMember().getId(),memberId));
    }

    private static boolean checkCommentIsMine(Long commentMemberId, Long memberId){
        if (commentMemberId==memberId) return true;
        return false;
    }

}

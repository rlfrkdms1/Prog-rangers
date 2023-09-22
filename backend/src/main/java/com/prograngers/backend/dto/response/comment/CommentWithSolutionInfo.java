package com.prograngers.backend.dto.response.comment;

import com.prograngers.backend.entity.comment.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentWithSolutionInfo {

    private CommentInfo comment;
    private SolutionWithProblemInfo solution;

    public static CommentWithSolutionInfo from(Comment comment) {
        return new CommentWithSolutionInfo(CommentInfo.from(comment), SolutionWithProblemInfo.from(comment.getSolution()));
    }
}

package com.prograngers.backend.dto.comment.response;

import com.prograngers.backend.entity.comment.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CommentWithSolutionResponse {

    private CommentResponse comment;
    private SolutionWithProblemResponse solution;

    public static CommentWithSolutionResponse from(Comment comment) {
        return new CommentWithSolutionResponse(CommentResponse.from(comment),
                SolutionWithProblemResponse.from(comment.getSolution()));
    }
}

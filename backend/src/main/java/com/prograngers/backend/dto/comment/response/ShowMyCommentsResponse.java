package com.prograngers.backend.dto.comment.response;

import com.prograngers.backend.entity.comment.Comment;
import java.util.List;
import java.util.stream.Collectors;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ShowMyCommentsResponse {

    private boolean hasNext;
    private List<CommentWithSolutionResponse> contents;

    public static ShowMyCommentsResponse from(Slice<Comment> commentPage) {
        return new ShowMyCommentsResponse(commentPage.hasNext(),
                commentPage.stream().map(comment -> CommentWithSolutionResponse.from(comment))
                        .collect(Collectors.toList()));
    }

}

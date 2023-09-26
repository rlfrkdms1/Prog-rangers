package com.prograngers.backend.dto.comment.response;

import com.prograngers.backend.entity.comment.Comment;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;
import java.util.List;
import java.util.stream.Collectors;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class ShowMyCommentsResponse {

    private boolean hasNext;
    private List<CommentWithSolutionInfo> contents;

    public static ShowMyCommentsResponse from(Slice<Comment> commentPage) {
        return new ShowMyCommentsResponse(commentPage.hasNext(),
                commentPage.stream().map(comment -> CommentWithSolutionInfo.from(comment)).collect(Collectors.toList()));
    }

}

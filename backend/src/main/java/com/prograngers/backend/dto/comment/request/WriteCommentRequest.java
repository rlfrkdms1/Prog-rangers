package com.prograngers.backend.dto.comment.request;

import static com.prograngers.backend.entity.comment.CommentStatusConstant.CREATED;

import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import jakarta.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WriteCommentRequest {
    @NotBlank(message = "댓글 내용을 입력해주세요")
    String content;
    Long parentId;

    public Comment toComment(Member member, Solution solution) {
        return Comment
                .builder()
                .member(member)
                .solution(solution)
                .content(content)
                .createdAt(LocalDateTime.now())
                .parentId(parentId)
                .status(CREATED)
                .build();
    }


}

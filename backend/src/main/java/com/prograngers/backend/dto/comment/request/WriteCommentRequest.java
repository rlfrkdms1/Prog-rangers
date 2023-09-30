package com.prograngers.backend.dto.comment.request;

import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

import static com.prograngers.backend.entity.comment.CommentStatusConStant.CREATED;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WriteCommentRequest {
    @NotBlank(message = "댓글 내용을 입력해주세요")
    String content;
    Long parentId;
    public Comment toComment(Member member, Solution solution){
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

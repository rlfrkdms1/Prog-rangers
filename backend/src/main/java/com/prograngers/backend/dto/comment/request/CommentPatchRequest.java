package com.prograngers.backend.dto.comment.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentPatchRequest {
    @NotBlank(message = "댓글 내용을 입력해주세요")
    private String content;
    private String mention;
}

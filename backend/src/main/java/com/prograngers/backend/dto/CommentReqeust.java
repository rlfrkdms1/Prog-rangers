package com.prograngers.backend.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CommentReqeust {

    @NotBlank(message = "댓글 작성자의 닉네임이 없습니다")
    String nickname;
    Integer orderParent;
    @NotBlank(message = "댓글 내용이 없습니다")
    String content;

    String mention;

    Long parentId;

    Integer groupNumber;

}

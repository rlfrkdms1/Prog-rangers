package com.prograngers.backend.dto.solution.response;


import com.prograngers.backend.entity.comment.CommentStatusConStant;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}

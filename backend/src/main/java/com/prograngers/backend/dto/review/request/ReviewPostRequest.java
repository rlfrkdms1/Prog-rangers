package com.prograngers.backend.dto.review.request;

import com.prograngers.backend.entity.Review;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ReviewPostRequest {

    @Nullable
    private Long parentId;
    @NotNull
    private int codeLineNumber;
    @NotBlank
    private String content;

    public Review toReview(Member member, Solution solution) {
        return Review.builder()
                .member(member)
                .solution(solution)
                .content(content)
                .codeLineNumber(codeLineNumber)
                .parentId(parentId)
                .createdAt(LocalDateTime.now())
                .build();
    }
}

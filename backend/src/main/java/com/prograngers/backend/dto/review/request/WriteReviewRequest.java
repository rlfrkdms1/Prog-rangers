package com.prograngers.backend.dto.review.request;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.solution.Solution;
import jakarta.annotation.Nullable;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class WriteReviewRequest {

    @Nullable
    private Long parentId;
    @NotNull(message = "줄 번호가 없습니다")
    private Integer codeLineNumber;
    @NotBlank(message = "내용은 빈칸일 수 없습니다")
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

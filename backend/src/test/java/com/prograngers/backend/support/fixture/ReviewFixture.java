package com.prograngers.backend.support.fixture;

import static com.prograngers.backend.entity.review.Review.ReviewBuilder;
import static com.prograngers.backend.entity.review.Review.builder;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.solution.Solution;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;


@AllArgsConstructor
public enum ReviewFixture {

    생성된_리뷰("리뷰 내용", false, 1),
    수정된_리뷰("수정 내용", true, 1);


    private final String content;
    private final boolean updated;
    private final int codeLineNumber;

    public ReviewBuilder 기본_빌더_생성(Member member, Solution solution, LocalDateTime createdAt) {
        return builder()
                .content(content)
                .updated(updated)
                .codeLineNumber(codeLineNumber)
                .member(member)
                .solution(solution)
                .createdAt(createdAt);
    }

    public Review 기본_정보_생성(Member member, Solution solution, LocalDateTime createdAt) {
        return 기본_빌더_생성(member, solution, createdAt).build();
    }

    public Review 아이디_지정_생성(Long id, Member member, Solution solution, LocalDateTime createdAt) {
        return 기본_빌더_생성(member, solution, createdAt).id(id).build();
    }


    public Review 부모_지정_생성(Long parentId, Long id, Member member, Solution solution, LocalDateTime createdAt) {
        return 기본_빌더_생성(member, solution, createdAt)
                .parentId(parentId)
                .id(id)
                .build();
    }
}

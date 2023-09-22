package com.prograngers.backend.support.fixture;

import com.prograngers.backend.entity.Review;
import com.prograngers.backend.entity.Review.ReviewBuilder;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;

import java.time.LocalDateTime;


public enum ReviewFixture {

    FIRST_LINE_REVIEW(1, "첫번째 줄 리뷰"),
    SECOND_LINE_REVIEW(2, "두번째 줄 리뷰"),
    THIRD_LINE_REVIEW(3, "세번째 줄 리뷰");

    private final int codeLineNumber;
    private final String content;

    ReviewFixture(int codeLineNumber, String content) {
        this.codeLineNumber = codeLineNumber;
        this.content = content;
    }

    public ReviewBuilder 기본_정보_빌더_생성(Member member, Solution solution, LocalDateTime createdAt) {
        return Review.builder()
                .content(this.content)
                .codeLineNumber(this.codeLineNumber)
                .member(member)
                .solution(solution)
                .createdAt(createdAt);
    }

    public Review 기본_정보_생성(Member member, Solution solution, LocalDateTime createdAt) {
        return 기본_정보_빌더_생성(member, solution, createdAt).build();
    }

}

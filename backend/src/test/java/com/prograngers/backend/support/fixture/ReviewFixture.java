package com.prograngers.backend.support.fixture;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.review.ReviewStatusConstant;
import com.prograngers.backend.entity.solution.Solution;
import lombok.AllArgsConstructor;
import java.time.LocalDateTime;
import static com.prograngers.backend.entity.review.Review.*;
import static com.prograngers.backend.entity.review.ReviewStatusConstant.CREATED;
import static com.prograngers.backend.entity.review.ReviewStatusConstant.DELETED;
import static com.prograngers.backend.entity.review.ReviewStatusConstant.FIXED;


@AllArgsConstructor
public enum ReviewFixture {

    생성된_리뷰("리뷰내용", CREATED, 1),
    수정된_리뷰("리뷰내용", FIXED, 2),
    삭제된_리뷰("리뷰내용", DELETED, 3);

    private final String content;
    private final ReviewStatusConstant status;
    private final int codeLineNumber;

    public ReviewBuilder 기본_빌더_생성(Member member, Solution solution, LocalDateTime createdDate){
        return Review.builder()
                .content(content)
                .status(status)
                .codeLineNumber(codeLineNumber)
                .member(member)
                .solution(solution)
                .createdAt(createdDate);
    }

    public Review 기본_정보_생성(Member member, Solution solution, LocalDateTime createdDate){
        return 기본_빌더_생성(member, solution, createdAt).build();
    }

    public Review 아이디_지정_생성(Long id,Member member, Solution solution, LocalDateTime createdDate){
        return 기본_빌더_생성(member, solution, createdAt).id(id).build();
    }


    public Review 부모_지정_생성(Long parentId, Long id, Member member, Solution solution, LocalDateTime createdAt) {
        return 기본_정보_빌더_생성(member, solution, createdAt)
                .parentId(parentId)
                .id(id)
                .build();
    }
}

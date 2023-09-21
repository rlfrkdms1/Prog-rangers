package com.prograngers.backend.support.fixture;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.review.ReviewStatusConStant;
import com.prograngers.backend.entity.solution.Solution;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

import static com.prograngers.backend.entity.review.Review.*;
import static com.prograngers.backend.entity.review.ReviewStatusConStant.*;


@AllArgsConstructor
public enum ReviewFixture {

    생성된_리뷰("리뷰내용", CREATED),
    수정된_리뷰("리뷰내용", FIXED),
    삭제된_리뷰("리뷰내용", DELETED);

    private final String content;
    private final ReviewStatusConStant status;

    public ReviewBuilder 기본_빌더_생성(){
        return builder()
                .content(content)
                .status(status);
    }

    public Review 기본_정보_생성(Member member, Solution solution, LocalDateTime createdDate){
        return 기본_빌더_생성()
                .member(member)
                .solution(solution)
                .createdAt(createdDate)
                .build();
    }

    public Review 아이디_지정_생성(Long id,Member member, Solution solution, LocalDateTime createdDate){
        return 기본_빌더_생성()
                .id(id)
                .member(member)
                .solution(solution)
                .createdAt(createdDate)
                .build();
    }

}
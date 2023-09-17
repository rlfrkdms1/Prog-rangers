package com.prograngers.backend.repository.review;

import com.prograngers.backend.entity.Review;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, QueryDslReviewRepository{
    List<Review> findAllBySolution(Solution solution);
    @Query("select distinct function('date_format', r.createdAt, '%d') from Review r where r.member.id = :memberId and function('date_format', r.createdAt, '%m') = :month")
    List<Integer> findAllByMonth(@Param("memberId") Long memberId, @Param("month") int month);
    List<Review> findAllByCodeLineNumberOrderByCreatedAtAsc(Integer codeLineNumber);
}

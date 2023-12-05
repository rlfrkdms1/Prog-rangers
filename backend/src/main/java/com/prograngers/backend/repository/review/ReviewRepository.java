package com.prograngers.backend.repository.review;

import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.solution.Solution;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReviewRepository extends JpaRepository<Review, Long>, QueryDslReviewRepository {
    List<Review> findAllBySolution(Solution solution);

    List<Review> findAllBySolutionOrderByCodeLineNumberAsc(Solution solution);

    @Query("select distinct function('date_format', r.createdAt, '%d') from Review r where r.member.id = :memberId and function('date_format', r.createdAt, '%m') = :month")
    List<Integer> findAllByMonth(@Param("memberId") Long memberId, @Param("month") int month);

    List<Review> findAllByCodeLineNumberOrderByCreatedAtAsc(Integer codeLineNumber);

    boolean existsById(Long id);
}

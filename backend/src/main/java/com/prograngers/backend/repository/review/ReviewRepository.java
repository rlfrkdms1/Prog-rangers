package com.prograngers.backend.repository.review;

import com.prograngers.backend.entity.Review;
import com.prograngers.backend.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReviewRepository extends JpaRepository<Review, Long>, QueryDslReviewRepository{
    List<Review> findAllBySolution(Solution solution);

    List<Review> findAllByCodeLineNumber(Integer codeLineNumber);

    List<Review> findAllByCodeLineNumberOrderByCreatedDateAsc(Integer codeLineNumber);
}

package com.prograngers.backend.repository.problem;

import com.prograngers.backend.entity.problem.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long>, QueryDslProblemRepository {
    Problem findByLink(String link);
}

package com.prograngers.backend.repository.problem;

import com.prograngers.backend.entity.problem.Problem;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long>, QueryDslProblemRepository {
    Optional<Problem> findByLink(String link);
}

package com.prograngers.backend.repository.problem;

import com.prograngers.backend.entity.Problem;
import org.springframework.data.domain.Example;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long>, QueryDslProblemRepository {
    Problem findByLink(String link);
}

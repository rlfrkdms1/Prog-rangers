package com.prograngers.backend.repository.problem;

import com.prograngers.backend.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long>, QueryDslProblemRepository {
    List<Problem> findAllByLink(String link);

}

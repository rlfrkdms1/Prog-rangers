package com.prograngers.backend.repository;

import com.prograngers.backend.entity.Problem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProblemRepository extends JpaRepository<Problem, Long> {
    List<Problem> findAllByOrderByDateDesc();
    List<Problem> findAllByLink(String link);
}

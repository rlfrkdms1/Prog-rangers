package com.prograngers.backend.repository;

import com.prograngers.backend.entity.Member;
import com.prograngers.backend.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long> {

    List<Solution> findAllByMember(Member member);

}
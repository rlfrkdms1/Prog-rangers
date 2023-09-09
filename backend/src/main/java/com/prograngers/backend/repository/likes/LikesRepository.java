package com.prograngers.backend.repository.likes;

import com.prograngers.backend.entity.Likes;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.entity.member.Member;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LikesRepository extends JpaRepository<Likes,Long> {
    List<Likes> findAllBySolution(Solution solution);
    Optional<Likes> findByMemberAndSolution(Member member, Solution solution);
}

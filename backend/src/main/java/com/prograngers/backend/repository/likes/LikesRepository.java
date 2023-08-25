package com.prograngers.backend.repository.likes;

import com.prograngers.backend.entity.Likes;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.entity.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LikesRepository extends JpaRepository<Likes,Long> {
    public List<Likes> findAllBySolution(Solution solution);
    public Likes findByMemberAndSolution(Member member, Solution solution);
}

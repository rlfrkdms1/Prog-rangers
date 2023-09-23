package com.prograngers.backend.repository.solution;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long>, QueryDslSolutionRepository{
    List<Solution> findAllByMember(Member member);
    List<Solution> findAllByScrapSolution(Solution solution);
    List<Solution> findTop3ByMemberOrderByCreatedAtDesc(Member member);

    @Query("select s from Solution s join Follow f on s.member.id = f.followingId where f.followerId = :memberId order by s.createdAt desc limit 5")
    List<Solution> findFollowingsRecentSolutions(@Param("memberId") Long memberId);

    @Query("select distinct function('date_format', s.createdAt, '%d') from Solution s where s.member.id = :memberId and function('date_format', s.createdAt, '%m') = :month")
    List<Integer> findAllByMonth(@Param("memberId") Long memberId, @Param("month") int month);

}

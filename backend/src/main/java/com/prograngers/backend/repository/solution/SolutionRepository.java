package com.prograngers.backend.repository.solution;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long>, QueryDslSolutionRepository{
    List<Solution> findAllByMember(Member member);
    List<Solution> findAllByScrapSolution(Solution solution);
    List<Solution> findTop3ByMemberOrderByCreatedDateDesc(Member member);

    @Query("select s from Solution s join Follow f on s.member.id = f.followingId where f.followerId = :memberId order by s.createdDate desc limit 5")
    List<Solution> findFollowingsRecentSolutions(@Param("memberId") Long memberId);
    List<Solution> findAllByMemberAndCreatedDateBetween(Member member, LocalDateTime startDate, LocalDateTime endDate);

    @Query("select distinct function('date_format', s.createdDate, '%d') from Solution s where s.member.id = :memberId and function('date_format', s.createdDate, '%m') = :month")
    List<Integer> findAllByMonth(Long memberId, int month);
}

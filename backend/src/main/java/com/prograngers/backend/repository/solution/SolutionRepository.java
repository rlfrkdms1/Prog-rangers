package com.prograngers.backend.repository.solution;

import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.solution.Solution;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface SolutionRepository extends JpaRepository<Solution, Long>, SolutionCustomRepository {
    List<Solution> findAllByMember(Member member);

    List<Solution> findAllByScrapSolution(Solution solution);

    Long countByScrapSolution(Solution solution);

    Long countByMember(Member member);

    List<Solution> findTop3ByMemberOrderByCreatedAtDesc(Member member);

    List<Solution> findAllByProblemOrderByCreatedAtAsc(Problem problem);

    @Query("select distinct function('date_format', s.createdAt, '%d') from Solution s where s.member.id = :memberId and function('date_format', s.createdAt, '%m') = :month")
    List<Integer> findAllByMonth(@Param("memberId") Long memberId, @Param("month") int month);

    @Query("select s from Solution s join Likes l on l.solution.id = s.id where l.member.id = :memberId order by l.createdAt desc")
    Slice<Solution> findMyLikesPage(@Param("memberId") Long memberId, Pageable pageable);
}

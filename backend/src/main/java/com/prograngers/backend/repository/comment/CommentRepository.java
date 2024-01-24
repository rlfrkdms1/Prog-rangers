package com.prograngers.backend.repository.comment;

import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.solution.Solution;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {
    List<Comment> findAllBySolution(Solution solution);

    List<Comment> findAllByParentId(Long parentId);

    List<Comment> findAllBySolutionOrderByCreatedAtAsc(Solution solution);

    boolean existsById(Long id);

    @Query("select c from Comment c join fetch c.member join fetch c.solution s join fetch s.problem join fetch s.member where c.member.id = :memberId order by c.createdAt")
    Slice<Comment> findMyPageByMemberId(Pageable pageable, @Param("memberId") Long memberId);
}

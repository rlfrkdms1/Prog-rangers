package com.prograngers.backend.repository.comment;

import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.solution.Solution;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>  {

    List<Comment> findAllBySolution(Solution solution);

    @Query("select c from Comment c join fetch c.member join fetch c.solution s join fetch s.problem join fetch s.member where c.member.id = :memberId ")
    Slice<Comment> findMyPageByMemberId(Pageable pageable, @Param("memberId") Long memberId);

}

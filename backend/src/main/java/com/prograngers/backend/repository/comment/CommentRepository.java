package com.prograngers.backend.repository.comment;

import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.solution.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>  {

    List<Comment> findAllBySolution(Solution solution);

}

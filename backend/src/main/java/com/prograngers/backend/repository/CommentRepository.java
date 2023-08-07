package com.prograngers.backend.repository;

import com.prograngers.backend.entity.Comment;
import com.prograngers.backend.entity.Solution;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long> {

    List<Comment> findAllBySolution(Solution solution);

}

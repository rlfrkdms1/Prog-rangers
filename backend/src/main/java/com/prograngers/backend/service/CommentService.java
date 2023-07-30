package com.prograngers.backend.service;

import com.prograngers.backend.entity.Comment;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> findBySolution(Solution solution){
        return commentRepository.findAllBySolution(solution);
    }

    public Comment findById(Long id){
        return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException());
    }
}

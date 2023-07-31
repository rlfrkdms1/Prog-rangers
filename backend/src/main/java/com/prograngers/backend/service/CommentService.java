package com.prograngers.backend.service;

import com.prograngers.backend.dto.CommentPatchRequest;
import com.prograngers.backend.entity.Comment;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.exception.notfound.CommentNotFoundException;
import com.prograngers.backend.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CommentService {

    private final CommentRepository commentRepository;

    public List<Comment> findBySolution(Solution solution) {
        return commentRepository.findAllBySolution(solution);
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException());
    }

    @Transactional(readOnly = false)
    public Comment updateComment(Long commentId, CommentPatchRequest commentPatchRequest) {
        Comment comment = findById(commentId);

        comment.updateContent(commentPatchRequest.getContent());
        comment.updateMention(commentPatchRequest.getMention());

        Comment saved = commentRepository.save(comment);

        return saved;
    }

    @Transactional(readOnly = false)
    public Comment deleteComment(Long commentId) {
        Comment comment = findById(commentId);
        commentRepository.delete(comment);
        return comment;
    }
}

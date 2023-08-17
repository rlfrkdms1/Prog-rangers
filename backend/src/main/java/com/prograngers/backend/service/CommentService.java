package com.prograngers.backend.service;

import com.prograngers.backend.dto.comment.request.CommentPatchRequest;
import com.prograngers.backend.entity.Comment;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.exception.notfound.CommentNotFoundException;
import com.prograngers.backend.repository.comment.CommentRepository;
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

    @Transactional
    public Long updateComment(Long commentId, CommentPatchRequest commentPatchRequest) {
        Comment comment = findById(commentId);

        comment.updateContent(commentPatchRequest.getContent());
        comment.updateMention(commentPatchRequest.getMention());

        Comment saved = commentRepository.save(comment);

        // 리다이렉트 하기 위해 Solution의 Id 반환
        return saved.getSolution().getId();
    }

    @Transactional
    public void deleteComment(Long commentId) {
        Comment comment = findById(commentId);
        comment.updateContent("삭제된 댓글입니다");
        commentRepository.save(comment);
    }
}

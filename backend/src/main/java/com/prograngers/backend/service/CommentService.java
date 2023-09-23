package com.prograngers.backend.service;

import com.prograngers.backend.dto.comment.request.CommentPatchRequest;
import com.prograngers.backend.dto.comment.request.CommentRequest;
import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.notfound.CommentAlreadyDeletedException;
import com.prograngers.backend.exception.notfound.CommentNotFoundException;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.exception.notfound.SolutionNotFoundException;
import com.prograngers.backend.exception.unauthorization.MemberUnAuthorizedException;
import com.prograngers.backend.repository.comment.CommentRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

import static com.prograngers.backend.entity.comment.CommentStatusConStant.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class CommentService {
    private final SolutionRepository solutionRepository;

    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;

    public List<Comment> findBySolution(Solution solution) {
        return commentRepository.findAllBySolution(solution);
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
    }

    // 댓글 작성
    @Transactional
    public void addComment(Long solutionId, CommentRequest commentRequest, Long memberId) {
        Solution solution = findSolutionById(solutionId);
        Member member = findMemberById(memberId);
        Comment comment = commentRequest.toComment(member,solution);
        commentRepository.save(comment);
    }

    @Transactional
    public Long updateComment(Long commentId, CommentPatchRequest commentPatchRequest, Long memberId) {
        Comment comment = findById(commentId);
        Long targetCommentMemberId = comment.getMember().getId();

        Member member = findMemberById(memberId);
        validMemberAuthorization(targetCommentMemberId, member);
        comment.update(commentPatchRequest.getContent());
        // 리다이렉트 하기 위해 Solution의 Id 반환
        return comment.getId();
    }

    @Transactional
    public void deleteComment(Long commentId,Long memberId) {
        Comment comment = findById(commentId);
        Member member = findMemberById(memberId);
        Long targetCommentMemberId = comment.getMember().getId();
        validMemberAuthorization(targetCommentMemberId, member);
        validCommentAlreadyDeleted(comment);
        comment.delete();
        commentRepository.save(comment);
    }

    private void validCommentAlreadyDeleted(Comment comment) {
        if (comment.getStatus().equals(DELETED)){
            throw new CommentAlreadyDeletedException();
        }
    }

    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
    }

    private Solution findSolutionById(Long solutionId) {
        return solutionRepository.findById(solutionId).orElseThrow(SolutionNotFoundException::new);
    }

    private void validMemberAuthorization(Long targetCommentMemberId, Member member) {
        if (!targetCommentMemberId.equals(member.getId())){
            throw new MemberUnAuthorizedException();
        }
    }
}

package com.prograngers.backend.service;

import static com.prograngers.backend.exception.errorcode.AuthErrorCode.UNAUTHORIZED_MEMBER;
import static com.prograngers.backend.exception.errorcode.CommentErrorCode.COMMENT_NOT_FOUND;
import static com.prograngers.backend.exception.errorcode.CommonErrorCode.DIFFERENT_SOLUTION;
import static com.prograngers.backend.exception.errorcode.CommonErrorCode.INVALID_PARENT;
import static com.prograngers.backend.exception.errorcode.MemberErrorCode.MEMBER_NOT_FOUND;
import static com.prograngers.backend.exception.errorcode.SolutionErrorCode.SOLUTION_NOT_FOUND;

import com.prograngers.backend.dto.comment.request.UpdateCommentRequest;
import com.prograngers.backend.dto.comment.request.WriteCommentRequest;
import com.prograngers.backend.dto.comment.response.ShowMyCommentsResponse;
import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.InvalidValueException;
import com.prograngers.backend.exception.NotFoundException;
import com.prograngers.backend.exception.UnAuthorizationException;
import com.prograngers.backend.repository.comment.CommentRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class CommentService {

    private final SolutionRepository solutionRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    private Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new NotFoundException(COMMENT_NOT_FOUND));
    }

    public ShowMyCommentsResponse showMyComments(Long memberId, Pageable pageable) {
        Slice<Comment> commentPage = commentRepository.findMyPageByMemberId(pageable, memberId);
        return ShowMyCommentsResponse.from(commentPage);
    }

    @Transactional
    public void addComment(Long solutionId, WriteCommentRequest writeCommentRequest, Long memberId) {
        Solution solution = findSolutionById(solutionId);
        Member member = findMemberById(memberId);
        if (writeCommentRequest.getParentId() != null) {
            validParentExists(writeCommentRequest);
            validSameSolution(writeCommentRequest, solutionId);
        }
        Comment comment = writeCommentRequest.toComment(member, solution);
        commentRepository.save(comment);
    }

    private void validSameSolution(WriteCommentRequest writeCommentRequest, Long solutionId) {
        if (commentRepository.findById(writeCommentRequest.getParentId()).get().getSolution().getId() != solutionId) {
            throw new InvalidValueException(DIFFERENT_SOLUTION);
        }
    }

    private void validParentExists(WriteCommentRequest writeCommentRequest) {
        if (!commentRepository.existsById(writeCommentRequest.getParentId())) {
            throw new InvalidValueException(INVALID_PARENT);
        }
    }

    @Transactional
    public Long updateComment(Long commentId, UpdateCommentRequest updateCommentRequest, Long memberId) {
        Comment comment = findById(commentId);
        Member member = findMemberById(memberId);
        validMemberAuthorization(comment, member);
        comment.update(updateCommentRequest.getContent());
        return comment.getId();
    }

    @Transactional
    public void deleteComment(Long commentId, Long memberId) {
        Comment comment = findById(commentId);
        Member member = findMemberById(memberId);
        validMemberAuthorization(comment, member);
        deleteChildren(comment);
        commentRepository.delete(comment);
    }

    private void deleteChildren(Comment comment) {
        commentRepository.deleteAll(commentRepository.findAllByParentId(comment.getId()));
    }


    private Member findMemberById(Long memberId) {
        return memberRepository.findById(memberId).orElseThrow(() -> new NotFoundException(MEMBER_NOT_FOUND));
    }

    private Solution findSolutionById(Long solutionId) {
        return solutionRepository.findById(solutionId).orElseThrow(() -> new NotFoundException(SOLUTION_NOT_FOUND));
    }

    private void validMemberAuthorization(Comment comment, Member member) {
        if (!comment.getMember().getId().equals(member.getId())) {
            throw new UnAuthorizationException(UNAUTHORIZED_MEMBER);
        }
    }
}

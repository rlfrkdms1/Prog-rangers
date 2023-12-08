package com.prograngers.backend.service;

import static com.prograngers.backend.entity.comment.CommentStatusConstant.DELETED;

import com.prograngers.backend.dto.comment.request.UpdateCommentRequest;
import com.prograngers.backend.dto.comment.request.WriteCommentRequest;
import com.prograngers.backend.dto.comment.response.ShowMyCommentsResponse;
import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.badrequest.DifferentSolutionException;
import com.prograngers.backend.exception.badrequest.InvalidPageNumberException;
import com.prograngers.backend.exception.badrequest.InvalidPageSizeException;
import com.prograngers.backend.exception.badrequest.InvalidParentException;
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
import org.springframework.data.domain.PageRequest;
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
        return commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
    }

    public ShowMyCommentsResponse showMyComments(Long memberId, Pageable pageable) {
        validPageableNumber(pageable);
        Slice<Comment> commentPage = commentRepository.findMyPageByMemberId(pageable, memberId);
        return ShowMyCommentsResponse.from(commentPage);
    }

    private void validPageableNumber(Pageable pageable) {
        validPageNumber(pageable);
        validPageSize(pageable);
    }

    private void validPageNumber(Pageable pageable) {
        if (pageable.getPageNumber() < 0) {
            throw new InvalidPageNumberException();
        }
    }

    private void validPageSize(Pageable pageable) {
        if (pageable.getPageSize() < 1) {
            throw new InvalidPageSizeException();
        }
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
            throw new DifferentSolutionException();
        }
    }

    private void validParentExists(WriteCommentRequest writeCommentRequest) {
        if (!commentRepository.existsById(writeCommentRequest.getParentId())) {
            throw new InvalidParentException();
        }
    }

    @Transactional
    public Long updateComment(Long commentId, UpdateCommentRequest updateCommentRequest, Long memberId) {
        Comment comment = findById(commentId);
        Long targetCommentMemberId = comment.getMember().getId();

        Member member = findMemberById(memberId);
        validMemberAuthorization(targetCommentMemberId, member);
        comment.update(updateCommentRequest.getContent());
        return comment.getId();
    }

    @Transactional
    public void deleteComment(Long commentId, Long memberId) {
        Comment comment = findById(commentId);
        Member member = findMemberById(memberId);
        Long targetCommentMemberId = comment.getMember().getId();
        validMemberAuthorization(targetCommentMemberId, member);
        validCommentAlreadyDeleted(comment);
        comment.delete();
        commentRepository.save(comment);
    }

    private void validCommentAlreadyDeleted(Comment comment) {
        if (comment.getStatus().equals(DELETED)) {
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
        if (!targetCommentMemberId.equals(member.getId())) {
            throw new MemberUnAuthorizedException();
        }
    }
}

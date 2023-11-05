package com.prograngers.backend.service;

import com.prograngers.backend.dto.comment.request.UpdateCommentRequest;
import com.prograngers.backend.dto.comment.request.WriteCommentRequest;
import com.prograngers.backend.dto.comment.response.ShowMyCommentsResponse;
import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.exception.badrequest.DifferentSolutionException;
import com.prograngers.backend.exception.badrequest.InvalidPageNumberException;
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
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.prograngers.backend.entity.comment.CommentStatusConstant.*;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
@Slf4j
public class CommentService {

    private final SolutionRepository solutionRepository;
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;

    private final int MY_COMMENTS_PAGE_SIZE = 3;

    private Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(CommentNotFoundException::new);
    }

    public ShowMyCommentsResponse showMyComments(Long memberId, Integer pageNumber) {
        validPageNumber(pageNumber);
        Slice<Comment> commentPage = commentRepository.findMyPageByMemberId(PageRequest.of(pageNumber - 1, MY_COMMENTS_PAGE_SIZE), memberId);
        return ShowMyCommentsResponse.from(commentPage);
    }

    private void validPageNumber(Integer pageNumber) {
        if (pageNumber < 1) {
            throw new InvalidPageNumberException();
        }
    }

    @Transactional
    public void addComment(Long solutionId, WriteCommentRequest writeCommentRequest, Long memberId) {
        Solution solution = findSolutionById(solutionId);
        Member member = findMemberById(memberId);
        validParentExists(writeCommentRequest);
        validSameSolution(writeCommentRequest, solutionId);
        Comment comment = writeCommentRequest.toComment(member, solution);
        commentRepository.save(comment);
    }

    private void validSameSolution(WriteCommentRequest writeCommentRequest, Long solutionId) {
        if (writeCommentRequest.getParentId() == null) {
            return;
        }
        if (commentRepository.findById(writeCommentRequest.getParentId()).get().getSolution().getId() != solutionId) {
            throw new DifferentSolutionException();
        }
    }

    private void validParentExists(WriteCommentRequest writeCommentRequest) {
        if (writeCommentRequest.getParentId() == null) {
            return;
        }
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

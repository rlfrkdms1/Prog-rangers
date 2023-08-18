package com.prograngers.backend.service;

import com.prograngers.backend.dto.comment.request.CommentPatchRequest;
import com.prograngers.backend.dto.comment.request.CommentReqeust;
import com.prograngers.backend.entity.Comment;
import com.prograngers.backend.entity.Member;
import com.prograngers.backend.entity.Solution;
import com.prograngers.backend.exception.notfound.CommentNotFoundException;
import com.prograngers.backend.exception.notfound.MemberNotFoundException;
import com.prograngers.backend.exception.notfound.SolutionNotFoundException;
import com.prograngers.backend.exception.unauthorization.MemberUnAuthorizedException;
import com.prograngers.backend.repository.comment.CommentRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@Service
@Transactional(readOnly = true)
public class CommentService {
    private final SolutionRepository solutionRepository;

    private final CommentRepository commentRepository;

    private final MemberRepository memberRepository;

    private final String DELETED_COMMENT = "삭제된 댓글입니다";

    public List<Comment> findBySolution(Solution solution) {
        return commentRepository.findAllBySolution(solution);
    }

    public Comment findById(Long id) {
        return commentRepository.findById(id).orElseThrow(() -> new CommentNotFoundException());
    }

    // 댓글 작성
    @Transactional
    public void addComment(Long solutionId, CommentReqeust commentReqeust, Long memberId) {

        Solution solution = solutionRepository.findById(solutionId).orElseThrow(SolutionNotFoundException::new);
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);

        Comment comment = Comment.builder().
                member(member).
                solution(solution).
                mention(commentReqeust.getMention()).
                content(commentReqeust.getContent()).
                date(LocalDate.now()).parentId(commentReqeust.getParentId())
                .build();

        Comment saved = commentRepository.save(comment);
    }
    @Transactional
    public Long updateComment(Long commentId, CommentPatchRequest commentPatchRequest, Long memberId) {
        Comment comment = findById(commentId);
        Long targetMemberId = comment.getMember().getId();

        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        if (targetMemberId!=member.getId()){
            throw new MemberUnAuthorizedException();
        }

        comment.updateContent(commentPatchRequest.getContent());
        comment.updateMention(commentPatchRequest.getMention());

        Comment saved = commentRepository.save(comment);

        // 리다이렉트 하기 위해 Solution의 Id 반환
        return saved.getSolution().getId();
    }

    @Transactional
    public void deleteComment(Long commentId,Long memberId) {
        Comment comment = findById(commentId);
        Member member = memberRepository.findById(memberId).orElseThrow(MemberNotFoundException::new);
        Long targetCommentMemberId = comment.getMember().getId();
        if (targetCommentMemberId!=member.getId()){
            throw new MemberUnAuthorizedException();
        }
        comment.updateContent(DELETED_COMMENT);
        commentRepository.save(comment);
    }
}

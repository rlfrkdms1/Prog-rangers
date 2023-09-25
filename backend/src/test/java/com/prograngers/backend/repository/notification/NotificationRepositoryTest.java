package com.prograngers.backend.repository.notification;

import com.prograngers.backend.entity.Notification;
import com.prograngers.backend.entity.comment.Comment;
import com.prograngers.backend.entity.member.Member;
import com.prograngers.backend.entity.problem.Problem;
import com.prograngers.backend.entity.review.Review;
import com.prograngers.backend.entity.solution.Solution;
import com.prograngers.backend.repository.comment.CommentRepository;
import com.prograngers.backend.repository.member.MemberRepository;
import com.prograngers.backend.repository.problem.ProblemRepository;
import com.prograngers.backend.repository.review.ReviewRepository;
import com.prograngers.backend.repository.solution.SolutionRepository;
import com.prograngers.backend.support.RepositoryTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import java.time.LocalDateTime;
import java.util.List;

import static com.prograngers.backend.entity.solution.LanguageConstant.JAVA;
import static com.prograngers.backend.support.fixture.CommentFixture.생성된_댓글;
import static com.prograngers.backend.support.fixture.MemberFixture.길가은;
import static com.prograngers.backend.support.fixture.MemberFixture.장지담;
import static com.prograngers.backend.support.fixture.NotificationFixture.댓글_알림;
import static com.prograngers.backend.support.fixture.NotificationFixture.리뷰_알림;
import static com.prograngers.backend.support.fixture.ProblemFixture.백준_문제;
import static com.prograngers.backend.support.fixture.ReviewFixture.FIRST_LINE_REVIEW;
import static com.prograngers.backend.support.fixture.SolutionFixture.공개_풀이;
import static org.assertj.core.api.Assertions.assertThat;

@RepositoryTest
class NotificationRepositoryTest {

    @Autowired
    private MemberRepository memberRepository;
    @Autowired
    private NotificationRepository notificationRepository;
    @Autowired
    private SolutionRepository solutionRepository;
    @Autowired
    private CommentRepository commentRepository;
    @Autowired
    private ReviewRepository reviewRepository;
    @Autowired
    private ProblemRepository problemRepository;

    @Test
    @DisplayName("회원이 주어졌을 때 회원에게 온 알림을 최신순으로 조회할 수 있다.")
    void 나의_최근_알림_조회(){
        Member member1 = 저장(길가은.기본_정보_생성());
        Member member2 = 저장(장지담.기본_정보_생성());

        Problem problem = 저장(백준_문제.기본_정보_생성());
        Solution solution = 저장(공개_풀이.기본_정보_생성(problem, member1, LocalDateTime.of(2023, 9, 3, 12, 0), JAVA, 1));
        Review review = 저장(FIRST_LINE_REVIEW.기본_정보_생성(member2, solution, LocalDateTime.now().minusHours(1)));
        Comment comment1 = 저장(생성된_댓글.기본_정보_생성(member2, solution, LocalDateTime.now().minusHours(3)));
        Comment comment2 = 저장(생성된_댓글.기본_정보_생성(member2, solution, LocalDateTime.now().minusHours(2)));

        Notification notification1 = 저장(리뷰_알림.생성_안읽음(member1, solution, review));
        Notification notification3 = 저장(댓글_알림.생성_안읽음(member1, solution, comment1));
        Notification notification2 = 저장(댓글_알림.생성_안읽음(member1, solution, comment2));

        List<Notification> notifications = notificationRepository.findTop9ByReceiverOrderByCreatedAtDesc(member1);

        assertThat(notifications).containsExactly(notification1, notification2, notification3);
    }
    private Member 저장(Member member) {
        return memberRepository.save(member);
    }
    private Problem 저장(Problem problem) {
        return problemRepository.save(problem);
    }
    private Solution 저장(Solution solution) {
        return solutionRepository.save(solution);
    }
    private Review 저장(Review review) {
        return reviewRepository.save(review);
    }
    private Comment 저장(Comment comment) {
        return commentRepository.save(comment);
    }
    private Notification 저장(Notification notification) {
        return notificationRepository.save(notification);
    }

}
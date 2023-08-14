package com.prograngers.backend.repository;

import com.prograngers.backend.TestConfig;
import com.prograngers.backend.entity.Comment;
import com.prograngers.backend.exception.notfound.CommentNotFoundException;
import com.prograngers.backend.repository.comment.CommentRepository;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import static com.prograngers.backend.fixture.CommentFixture.댓글1;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Slf4j
@Import(TestConfig.class)
class CommentRepositoryTest {

    @Autowired
    private CommentRepository commentRepository;

    @DisplayName("댓글을 저장할 수 있다")
    @Test
    void 저장_테스트() {
        // given
        Comment comment = 댓글1.기본_댓글_생성(null);

        // when
        Comment saved = commentRepository.save(comment);

        // then
        Assertions.assertThat(saved).isEqualTo(comment);
    }

    @DisplayName("댓글을 수정할 수 있다")
    @Test
    void 수정_테스트() {
        // given
        Comment comment = 댓글1.기본_댓글_생성(null);
        commentRepository.save(comment);
        comment.updateContent("수정 내용");

        // when
        Comment updated = commentRepository.save(comment);

        // then
        Assertions.assertThat(updated).isEqualTo(comment);
    }

    @DisplayName("댓글을 삭제할 수 있다")
    @Test
    void 삭제_테스트() {
        // given
        Comment comment = 댓글1.기본_댓글_생성(null);
        Comment saved = commentRepository.save(comment);

        // when
        commentRepository.delete(comment);

        // then
        org.junit.jupiter.api.Assertions.assertThrows(CommentNotFoundException.class, () -> commentRepository.findById(saved.getId()).orElseThrow(() -> new CommentNotFoundException()));

    }


}
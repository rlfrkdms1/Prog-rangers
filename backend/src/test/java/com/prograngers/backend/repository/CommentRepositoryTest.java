package com.prograngers.backend.repository;

import com.prograngers.backend.entity.Comment;
import com.prograngers.backend.exception.notfound.CommentNotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@DataJpaTest
@Slf4j
class CommentRepositoryTest {

    @Autowired
    private  CommentRepository commentRepository;

    @Test
    void 저장_테스트(){
        // given
        Comment comment = Comment.builder()
                .content("댓글 내용")
                .build();

        // when
        Comment saved = commentRepository.save(comment);

        // then
        Assertions.assertThat(saved).isEqualTo(comment);
    }

    @Test
    void 수정_테스트(){
        // given
        Comment comment = Comment.builder()
                .content("댓글 내용")
                .build();
        commentRepository.save(comment);

        // when
        comment.updateContent("수정 내용");
        Comment updated = commentRepository.save(comment);

        // then
        Assertions.assertThat(updated).isEqualTo(comment);
    }

    @Test
    void 삭제_테스트(){
        // given
        Comment comment = Comment.builder()
                .content("댓글 내용")
                .build();
        commentRepository.save(comment);
        Comment saved = commentRepository.findById(1L).orElseThrow(() -> new CommentNotFoundException());
        Assertions.assertThat(saved.getId()).isEqualTo(1L);

        // when
        commentRepository.delete(comment);

        // then
        org.junit.jupiter.api.Assertions.assertThrows(
                CommentNotFoundException.class,
                ()-> commentRepository.findById(1L).orElseThrow(()->new CommentNotFoundException())
        );

    }



}
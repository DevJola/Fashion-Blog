package com.devjola.fashionblog.repository;

import com.devjola.fashionblog.enums.Role;
import com.devjola.fashionblog.model.Category;
import com.devjola.fashionblog.model.Comment;
import com.devjola.fashionblog.model.Post;
import com.devjola.fashionblog.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class CommentRepositoryTest {


    @Autowired
    private CommentRepository commentRepositoryUnderTest;

    @Test
    void itChecksIfCommentCanNotBeFoundByPostId() {
        //given
        long postId = 1L;

        Comment comment = new Comment();
        //when
        List<Comment> expected = commentRepositoryUnderTest
                .findCommentsByPostId(postId);
        //then
        assertThat(comment).isNotIn(expected);

    }
}
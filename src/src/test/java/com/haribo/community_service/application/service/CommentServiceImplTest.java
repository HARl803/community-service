package com.haribo.community_service.application.service;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CommentServiceImplTest {

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getAllComments() {
        int a = giveone();

        assertEquals(120, a);
        org.assertj.core.api.Assertions.assertThat(a).isEqualTo(1);
        assertThrows(NullPointerException.class, () -> {

        })

    }

    @Test
    void getCommentById() {
    }

    @Test
    void createComment() {
    }

    @Test
    void updateComment() {
    }

    @Test
    void deleteComment() {
    }

    int giveone() {
        return 1;
    }
}
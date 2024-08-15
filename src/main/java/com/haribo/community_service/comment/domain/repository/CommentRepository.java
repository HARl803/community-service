package com.haribo.community_service.comment.domain.repository;

import com.haribo.community_service.comment.application.dto.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CommentRepository extends JpaRepository<Comment, String> {
    List<Comment> findByCommentAuthorIdAndDeleteFlagCommentFalse(String userId);
    List<Comment> findByPostIdAndDeleteFlagCommentFalse(String postId);
    Optional<Comment> findByCommentIdAndDeleteFlagCommentFalse(String commentId);
}

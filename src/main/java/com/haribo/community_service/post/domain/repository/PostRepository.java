package com.haribo.community_service.post.domain.repository;

import com.haribo.community_service.post.application.dto.Post;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, String> {
    List<Post> findByDeleteFlagPostFalse();
    Optional<Post> findByPostIdAndDeleteFlagPostFalse(String postId);

    @Query(value = "SELECT * FROM post WHERE delete_flag_post = FALSE ORDER BY post_created_date DESC LIMIT 5", nativeQuery = true)
    Optional<List<Post>> findTop5ByOrderBycreatedDatetDesc();
}

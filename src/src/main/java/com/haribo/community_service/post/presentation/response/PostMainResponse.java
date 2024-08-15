package com.haribo.community_service.post.presentation.response;

import com.haribo.community_service.post.application.dto.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostMainResponse {

    @NotBlank
    String postId;

    @NotBlank
    String nickName;

    @NotBlank
    String postTitle;

    @NotBlank
    LocalDateTime postCreatedDate;

    public static PostMainResponse fromEntity(Post post) {
        return new PostMainResponse(
                post.getPostId(),
                post.getNickName(),
                post.getPostTitle(),
                post.getPostCreatedDate()
        );
    }
}

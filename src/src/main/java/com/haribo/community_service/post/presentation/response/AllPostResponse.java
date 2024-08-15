package com.haribo.community_service.post.presentation.response;

import com.haribo.community_service.post.application.dto.Post;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class AllPostResponse {

    @NotBlank
    private String postId;

    @NotBlank
    private String nickName;

    @NotBlank
    private long postNum;

    @NotBlank
    private String postTypeId;

    @NotBlank
    private String postTitle;

    @NotBlank
    private LocalDateTime postCreatedDate;

    public static AllPostResponse fromEntity (Post post) {
        return new AllPostResponse(
                post.getPostId(),
                post.getNickName(),
                post.getPostNum(),
                post.getPostType(),
                post.getPostTitle(),
                post.getPostCreatedDate());
    }
}

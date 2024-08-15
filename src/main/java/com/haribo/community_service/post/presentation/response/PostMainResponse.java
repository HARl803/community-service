package com.haribo.community_service.post.presentation.response;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostMainResponse {

    @NotBlank
    String postId;

    @NotBlank
    String postTitle;

    @NotBlank
    String postAuthorId;

    @NotBlank
    LocalDateTime postCreatedDate;
}

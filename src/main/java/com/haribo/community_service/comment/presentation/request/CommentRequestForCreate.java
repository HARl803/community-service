package com.haribo.community_service.comment.presentation.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestForCreate {

    @NotBlank
    private String postId;

    @NotBlank
    private String commentContent;
}

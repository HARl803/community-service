package com.haribo.community_service.post.presentation.response;

import com.haribo.community_service.post.application.dto.Post;
import com.haribo.community_service.comment.presentation.response.CommentResponse;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostResponse implements Serializable {

    @NotBlank
    private String postId;

    @NotBlank
    private String nickName;

    @NotBlank
    private String postTypeId;

    @NotBlank
    private String postTitle;

    @NotBlank
    private String postContent;

    private String postImageFile;

    @NotBlank
    private LocalDateTime postCreatedDate;

    @NotBlank
    private LocalDateTime postModifiedDate;

    private List<CommentResponse> comments;

    public static PostResponse fromEntity(Post post, List<CommentResponse> commentList) {
        return new PostResponse(
                post.getPostId(),
                post.getNickName(),
                post.getPostType(),
                post.getPostTitle(),
                post.getPostContent(),
                post.getPostImageFile(),
                post.getPostCreatedDate(),
                post.getPostModifiedDate(),
                commentList);
    }
}


package com.haribo.community_service.post.presentation.response;

import com.haribo.community_service.post.application.dto.Post;
import com.haribo.community_service.comment.presentation.response.CommentResponse;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
@AllArgsConstructor
public class PostResponse implements Serializable {

    private String postId;
    private String postAuthorId;
    private String postTypeId;
    private String postTitle;
    private String postContent;
    private String postImageFile;
    private LocalDateTime postCreatedDate;
    private LocalDateTime postModifiedDate;

    private List<CommentResponse> comments;

    public static PostResponse fromEntity(Post post, List<CommentResponse> commentList) {
        return new PostResponse(
                post.getPostId(),
                post.getPostAuthorId(),
                post.getPostType(),
                post.getPostTitle(),
                post.getPostContent(),
                post.getPostImageFile(),
                post.getPostCreatedDate(),
                post.getPostModifiedDate(),
                commentList);
    }
}


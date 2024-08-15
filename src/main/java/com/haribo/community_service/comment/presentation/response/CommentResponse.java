package com.haribo.community_service.comment.presentation.response;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import com.haribo.community_service.comment.application.dto.Comment;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
public class CommentResponse implements Serializable {

    @NotBlank
    private String commentId;

    @NotBlank
    private String commentAuthorId;

    @NotBlank
    private String commentContent;

    @NotBlank
    private LocalDateTime commentCreatedDate;

    public static CommentResponse fromEntity(Comment comment) {
        return new CommentResponse(
                comment.getCommentId(),
                comment.getCommentAuthorId(),
                comment.getCommentContent(),
                comment.getCommentCreatedDate()
        );
    }
}

package com.haribo.community_service.comment.application.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Comment {
    @Id
    @Setter
    private String commentId;

    @NotBlank
    private String postId;

    @NotBlank
    @Column(name = "comment_author_id")
    private String commentAuthorId;

    @NotBlank
    @Column(name = "nick_name")
    private String nickName;

    @NotBlank
    private String commentContent;

    private LocalDateTime commentCreatedDate;

    @CreationTimestamp
    private LocalDateTime commentModifiedDate;

    @Setter
    private boolean deleteFlagComment;
}

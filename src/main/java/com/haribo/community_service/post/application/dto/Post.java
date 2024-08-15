package com.haribo.community_service.post.application.dto;

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
public class Post {
    @Id
    @Setter
    private String postId;

    @NotBlank
    @Column(name = "post_author_id")
    private String postAuthorId;

    @NotBlank
    @Column(name = "post_type_id")
    private String postType;

    @NotBlank
    @Column(name = "nick_name")
    private String nickName;

    @NotBlank
    private String postTitle;

    @NotBlank
    private String postContent;

    private String postImageFile;

    private LocalDateTime postCreatedDate;

    @CreationTimestamp
    private LocalDateTime postModifiedDate;

    @Setter
    private boolean deleteFlagPost;

    @Setter
    private long postNum;
}

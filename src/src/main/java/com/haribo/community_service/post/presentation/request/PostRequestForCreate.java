package com.haribo.community_service.post.presentation.request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PostRequestForCreate {
    @NotBlank
    private String postTypeId;

    @NotBlank
    private String postTitle;

    @NotBlank
    private String postContent;
}

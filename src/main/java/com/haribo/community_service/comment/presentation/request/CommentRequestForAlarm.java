package com.haribo.community_service.comment.presentation.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CommentRequestForAlarm {

    @NotBlank
    private String receiver;

    @NotBlank
    private String add;

    @NotBlank
    private String typeId;
}

package com.haribo.community_service.comment.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.haribo.community_service.comment.presentation.request.CommentRequestForCreate;
import com.haribo.community_service.comment.presentation.request.CommentRequestForUpdate;
import com.haribo.community_service.comment.presentation.response.CommentResponse;

import java.net.URISyntaxException;
import java.util.List;

public interface CommentService {

    List<CommentResponse> getCommentsByUserId(String profileId);
    CommentResponse createComment(String profileId, CommentRequestForCreate request) throws URISyntaxException, JsonProcessingException;
    void updateComment(String profileId, CommentRequestForUpdate request);
    void deleteComment(String profileId, String commentId);
    String generatePrimaryKey();
}
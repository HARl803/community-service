package com.haribo.community_service.comment.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.haribo.community_service.comment.presentation.request.CommentRequestForCreate;
import com.haribo.community_service.comment.presentation.request.CommentRequestForUpdate;
import com.haribo.community_service.comment.presentation.response.CommentResponse;

import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;

public interface CommentService {

    List<CommentResponse> getCommentsByUserId(LinkedHashMap<String, String> profile);
    CommentResponse createComment(LinkedHashMap<String, String> profile, CommentRequestForCreate request) throws URISyntaxException, JsonProcessingException;
    void updateComment(LinkedHashMap<String, String> profile, CommentRequestForUpdate request);
    void deleteComment(LinkedHashMap<String, String> profile, String commentId);
    String generatePrimaryKey();
}
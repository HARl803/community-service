package com.haribo.community_service.post.application.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.haribo.community_service.post.presentation.request.PostRequestForCreate;
import com.haribo.community_service.post.presentation.request.PostRequestForUpdate;
import com.haribo.community_service.post.presentation.response.AllPostResponse;
import com.haribo.community_service.post.presentation.response.PostMainResponse;
import com.haribo.community_service.post.presentation.response.PostResponse;
import org.apache.tomcat.util.json.ParseException;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface PostService {

    List<PostMainResponse> getTop5Posts();
    List<AllPostResponse> getAllPosts();
    PostResponse getPostById(String postId) throws ParseException;
    PostResponse createPost(String profileId, PostRequestForCreate request, MultipartFile imageUrl) throws JsonProcessingException;
    void updatePost(String profileId, PostRequestForUpdate request, MultipartFile imageUrl) throws JsonProcessingException;
    void deletePost(String profileId, String postId) throws JsonProcessingException;
    String generatePrimaryKey();
    long generateOrderNum();
}

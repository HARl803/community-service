package com.haribo.community_service.post.presentation;

import com.haribo.community_service.common.auth.AuthService;
import com.haribo.community_service.post.application.service.PostServiceImpl;
import com.haribo.community_service.post.presentation.request.PostRequestForCreate;
import com.haribo.community_service.post.presentation.request.PostRequestForUpdate;
import com.haribo.community_service.post.presentation.response.PostResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.net.URISyntaxException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community/post")
public class PostController {

    private final PostServiceImpl postServiceImpl;
    private final AuthService authService;

    @GetMapping("/main")
    public ResponseEntity<?> getMainPosts() {

        return ResponseEntity.status(HttpStatus.OK).body(postServiceImpl.getTop5Posts());
    }

    @GetMapping
    public ResponseEntity<?> getAllPosts() {

        return ResponseEntity.status(HttpStatus.OK).body(postServiceImpl.getAllPosts());
    }

    @GetMapping("/{postId}")
    public ResponseEntity<PostResponse> getPostById(@PathVariable("postId") String postId) {

        return ResponseEntity.status(HttpStatus.OK).body(postServiceImpl.getPostById(postId));
    }

    @PostMapping
    public ResponseEntity<?> createPost(
            @RequestPart PostRequestForCreate request,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @CookieValue("JSESSIONID") String sessionId) throws URISyntaxException {

        String profileId = authService.authorizedProfileId(sessionId);

        return ResponseEntity.status(HttpStatus.CREATED).body(postServiceImpl.createPost(profileId, request, file));
    }

    @PatchMapping
    public ResponseEntity<?> updatePost(
            @RequestPart PostRequestForUpdate request,
            @RequestPart(value = "file", required = false) MultipartFile file,
            @CookieValue("JSESSIONID") String sessionId) throws URISyntaxException {

        String profileId = authService.authorizedProfileId(sessionId);

            postServiceImpl.updatePost(profileId, request, file);
            return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deletePost(@RequestBody String postId,
                                        @CookieValue("JSESSIONID") String sessionId) throws URISyntaxException {

        String profileId = authService.authorizedProfileId(sessionId);

        postServiceImpl.deletePost(profileId, postId);
        return (ResponseEntity<?>) ResponseEntity.status(HttpStatus.NO_CONTENT);
    }
}

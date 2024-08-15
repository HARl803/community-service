package com.haribo.community_service.comment.presentation;

import com.haribo.community_service.comment.application.service.CommentServiceImpl;
import com.haribo.community_service.comment.presentation.request.CommentRequestForCreate;
import com.haribo.community_service.comment.presentation.request.CommentRequestForUpdate;
import com.haribo.community_service.comment.presentation.response.CommentResponse;
import com.haribo.community_service.common.auth.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.LinkedHashMap;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/community/comment")
public class CommentController {

    private final CommentServiceImpl commentServiceImpl;
    private final AuthService authService;

    @GetMapping
    public ResponseEntity<List<CommentResponse>> getCommentsByUserId(@CookieValue("JSESSIONID") String sessionId) throws URISyntaxException {

        LinkedHashMap<String, String> profile = authService.authorizedProfileId(sessionId);

        return ResponseEntity.status(HttpStatus.OK).body(commentServiceImpl.getCommentsByUserId(profile));
    }

    @PostMapping
    public ResponseEntity<?> createComment(@CookieValue("JSESSIONID") String sessionId, @RequestBody CommentRequestForCreate request) throws URISyntaxException {

        log.info("세션 스트링: {}", sessionId);

        LinkedHashMap<String, String> profile = authService.authorizedProfileId(sessionId);

        return ResponseEntity.status(HttpStatus.CREATED).body(commentServiceImpl.createComment(profile, request));
    }

    @PatchMapping
    public ResponseEntity<?> updateComment(@CookieValue("JSESSIONID") String sessionId, @RequestBody CommentRequestForUpdate request) throws URISyntaxException {

        log.info("세션 스트링: {}", sessionId);

        LinkedHashMap<String, String> profile = authService.authorizedProfileId(sessionId);

        commentServiceImpl.updateComment(profile, request);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @DeleteMapping()
    public ResponseEntity<?> deleteComment(@CookieValue("JSESSIONID") String sessionId, @RequestBody String commentId) throws URISyntaxException {

        log.info("세션 스트링: {}", sessionId);

        LinkedHashMap<String, String> profile = authService.authorizedProfileId(sessionId);

        commentServiceImpl.deleteComment(profile, commentId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }


}

package com.haribo.community_service.comment.application.service;

import com.haribo.community_service.comment.application.dto.Comment;
import com.haribo.community_service.comment.domain.repository.CommentRepository;
import com.haribo.community_service.post.domain.repository.PostRepository;
import com.haribo.community_service.common.exception.CustomErrorCode;
import com.haribo.community_service.common.exception.CustomException;
import com.haribo.community_service.comment.presentation.request.CommentRequestForCreate;
import com.haribo.community_service.comment.presentation.request.CommentRequestForUpdate;

import com.haribo.community_service.comment.presentation.response.CommentResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URISyntaxException;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;
    private final PostRepository postRepository;
    private final RestTemplate restTemplate;

    @Value("${path.to.noti}")
    private String pathToNoti;

    @Override
    public List<CommentResponse> getCommentsByUserId(LinkedHashMap<String, String> profile) {

        String profileId = profile.get("profileId");

        log.info("유저별 작성한 게시글 조회하기! profileId: {}", profileId);

        return commentRepository.findByCommentAuthorIdAndDeleteFlagCommentFalse(profileId).stream()
                .map(CommentResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public CommentResponse createComment(LinkedHashMap<String, String> profile, CommentRequestForCreate request) throws URISyntaxException {

        String profileId = profile.get("profileId");
        String nickName = profile.get("nickName");

        log.info("코멘트 생성하기! profileId: {}", profileId);

        postRepository.findByPostIdAndDeleteFlagPostFalse(request.getPostId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.POST_NOT_FOUND));

        String generatedId = generatePrimaryKey();

        Comment comment = Comment.builder()
                .commentId(generatedId)
                .postId(request.getPostId())
                .commentAuthorId(profileId)
                .nickName(nickName)
                .commentContent(request.getCommentContent())
                .commentCreatedDate(LocalDateTime.now())
                .commentModifiedDate(LocalDateTime.now())
                .deleteFlagComment(false)
                .build();

        commentRepository.save(comment);

        if(commentRepository.existsById(generatedId)) {

            log.info("알람 처리 부분 주석 해놨어요..!");

//            Post post = postRepository.findByPostIdAndDeleteFlagPostFalse(request.getPostId())
//                    .orElseThrow(()-> new CustomException(CustomErrorCode.POST_NOT_FOUND));
//
//            HttpHeaders headers = new HttpHeaders();
//            headers.setContentType(MediaType.APPLICATION_JSON);
//            HttpEntity<CommentRequestForAlarm> requestEntity = new HttpEntity<>(CommentRequestForAlarm.builder()
//                    .receiver(post.getPostAuthorId())
//                    .add(post.getPostId())
//                    .typeId("NT06")
//                    .build(), headers);
//
//            ResponseEntity<String> alarmRes = restTemplate.exchange(new URI(pathToNoti), HttpMethod.POST, requestEntity, String.class);
//            log.info("알람 서버 요청 성공: {}", requestEntity);

            return CommentResponse.fromEntity(comment);
        }
        else throw new CustomException(CustomErrorCode.COMMENT_NOT_CREATED);
    }

    @Override
    public void updateComment(LinkedHashMap<String, String> profile, CommentRequestForUpdate request) {

        String profileId = profile.get("profileId");
        String nickName = profile.get("nickName");

        log.info("코멘트 업데이트! profileId: {}", profileId);

        Comment commentBf = commentRepository.findByCommentIdAndDeleteFlagCommentFalse(request.getCommentId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.COMMENT_NOT_FOUND));

        if(profileId.equals(commentBf.getCommentAuthorId())) {
            Comment commentAf = Comment.builder()
                    .commentId(request.getCommentId())
                    .postId(commentBf.getPostId())
                    .commentAuthorId(profileId)
                    .nickName(nickName)
                    .commentContent(request.getCommentContent())
                    .commentCreatedDate(commentBf.getCommentCreatedDate())
                    .commentModifiedDate(LocalDateTime.now())
                    .deleteFlagComment(false)
                    .build();

            commentRepository.save(commentAf);
        } else throw new CustomException(CustomErrorCode.COMMENT_NOT_UPDATED);
    }

    @Override
    public void deleteComment(LinkedHashMap<String, String> profile, String commentId) {

        String profileId = profile.get("profileId");

        log.info("코멘트 삭제! profileId: {}, commentId: {}", profileId, commentId);

        Comment comment = commentRepository.findByCommentIdAndDeleteFlagCommentFalse(commentId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.COMMENT_NOT_FOUND));

        if(profileId.equals(comment.getCommentAuthorId())) {
            comment.setDeleteFlagComment(true);

            commentRepository.save(comment);
        } else throw new CustomException(CustomErrorCode.DELETE_COMMENT_FAILED);
    }

    @Override
    public String generatePrimaryKey() {
        
        log.info("pk 생성");
        
        String prefix = "CC";
        String uuid = UUID.randomUUID().toString();

        return prefix + uuid;
    }
}

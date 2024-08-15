package com.haribo.community_service.post.application.service;

import com.haribo.community_service.comment.application.dto.Comment;
import com.haribo.community_service.comment.domain.repository.CommentRepository;
import com.haribo.community_service.comment.presentation.response.CommentResponse;
import com.haribo.community_service.common.amazonS3.S3ServiceImpl;
import com.haribo.community_service.common.exception.CustomErrorCode;
import com.haribo.community_service.common.exception.CustomException;
import com.haribo.community_service.post.application.dto.Post;
import com.haribo.community_service.post.domain.repository.PostRepository;
import com.haribo.community_service.post.presentation.request.PostRequestForCreate;
import com.haribo.community_service.post.presentation.request.PostRequestForUpdate;
import com.haribo.community_service.post.presentation.response.AllPostResponse;
import com.haribo.community_service.post.presentation.response.PostMainResponse;
import com.haribo.community_service.post.presentation.response.PostResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;
    private final S3ServiceImpl s3Service;
    private final CommentRepository commentRepository;

    @Override
    public List<PostMainResponse> getTop5Posts() {

        List<Post> postList = postRepository.findTop5ByOrderBycreatedDatetDesc()
                .orElseThrow(()->new CustomException(CustomErrorCode.POST_NOT_FOUND));

        return postList.stream()
                .map(PostMainResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public List<AllPostResponse> getAllPosts() {

        log.info("모든 게시글 조회: 게시판 리스트!");

        List<Post> posts = postRepository.findByDeleteFlagPostFalse();

        return posts.stream()
                .map(AllPostResponse::fromEntity)
                .collect(Collectors.toList());
    }

    @Override
    public PostResponse getPostById(String postId) {

        log.info("게시글 하나 조회하기!");

        Post post = postRepository.findByPostIdAndDeleteFlagPostFalse(postId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.POST_NOT_FOUND));

        List<Comment> comments = commentRepository.findByPostIdAndDeleteFlagCommentFalse(post.getPostId());
        List<CommentResponse> commentResponseList = new ArrayList<>();

        for (Comment comment : comments) {
            CommentResponse commentResponse = CommentResponse.builder()
                    .commentId(comment.getCommentId())
                    .nickName(comment.getNickName())
                    .commentContent(comment.getCommentContent())
                    .commentCreatedDate(comment.getCommentCreatedDate())
                    .build();
            commentResponseList.add(commentResponse);
        }

        return PostResponse.fromEntity(post, commentResponseList);
    }

    @Override
    public PostResponse createPost(LinkedHashMap<String, String> profile, PostRequestForCreate request, MultipartFile file) {

        log.info("글 작성하기!");

        String profileId = profile.get("profileId");
        String nickName = profile.get("nickName");

        String generatedId = generatePrimaryKey();

        String imageUrl = Optional.ofNullable(s3Service.uploadFile(file)).orElse("");

        Post post = Post.builder()
                .postId(generatedId)
                .postAuthorId(profileId)
                .postType(request.getPostTypeId())
                .postTitle(request.getPostTitle())
                .nickName(nickName)
                .postContent(request.getPostContent())
                .postCreatedDate(LocalDateTime.now())
                .postModifiedDate(LocalDateTime.now())
                .postImageFile(imageUrl)
                .deleteFlagPost(false)
                .postNum(generateOrderNum())
                .build();

        postRepository.save(post);

        if(postRepository.existsById(generatedId)) return PostResponse.fromEntity(post, new ArrayList<>());
        else throw new CustomException(CustomErrorCode.POST_NOT_CREATED);
    }

    @Override
    public void updatePost(LinkedHashMap<String, String> profile, PostRequestForUpdate request, MultipartFile file) {

        log.info("글 수정 하기!");

        String profileId = profile.get("profileId");
        String nickName = profile.get("nickName");

        Post postBf = postRepository.findByPostIdAndDeleteFlagPostFalse(request.getPostId())
                .orElseThrow(() -> new CustomException(CustomErrorCode.POST_NOT_FOUND));

        String imageUrl = Optional.ofNullable(s3Service.uploadFile(file)).orElse(postBf.getPostImageFile());

        if(postBf.getPostAuthorId().equals(profileId)) {
            Post postAf = Post.builder()
                    .postId(postBf.getPostId())
                    .postAuthorId(profileId)
                    .postType(request.getPostTypeId())
                    .postTitle(request.getPostTitle())
                    .nickName(nickName)
                    .postContent(request.getPostContent())
                    .postImageFile(imageUrl)
                    .postCreatedDate(postBf.getPostCreatedDate())
                    .postModifiedDate(LocalDateTime.now())
                    .deleteFlagPost(false)
                    .postNum(postBf.getPostNum())
                    .build();

            postRepository.save(postAf);
        } else throw new CustomException(CustomErrorCode.POST_NOT_UPDATED);
    }

    @Override
    public void deletePost(LinkedHashMap<String, String> profile, String postId) {

        String profileId = profile.get("profileId");
        String nickName = profile.get("nickName");

        log.info("글 삭제하기!");

        // id로 찾고, flag가 false인 것만 찾기
        Post post = postRepository.findByPostIdAndDeleteFlagPostFalse(postId)
                .orElseThrow(() -> new CustomException(CustomErrorCode.POST_NOT_FOUND));

        if(post.getPostAuthorId().equals(profileId)) {
            post.setDeleteFlagPost(true);
            postRepository.save(post);

            log.info("게시글과 연결되어 있던 코멘트 모두 삭제하기!");

            List<Comment> commentListByPost = commentRepository.findByPostIdAndDeleteFlagCommentFalse(post.getPostId());

            for (Comment comment : commentListByPost) {
                comment.setDeleteFlagComment(true);
                commentRepository.save(comment);
            }
        } else throw new CustomException(CustomErrorCode.DELETE_POST_FAILED);
    }

    @Override
    public String generatePrimaryKey() {

        log.info("pk 생성");

        String prefix = "CP";
        String uuid = UUID.randomUUID().toString();

        return prefix + uuid;
    }

    @Override
    public long generateOrderNum() {
        return postRepository.count() + 1;
    }
}

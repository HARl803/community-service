package com.haribo.community_service.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum CustomErrorCode implements ErrorCode{

    POST_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 게시글입니다."),
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, "존재하지 않는 유저입니다."),
    IMAGE_NOT_SAVED_IN_S3(HttpStatus.BAD_REQUEST, "이미지가 S3에 저장되지 않았습니다."),
    POST_NOT_CREATED(HttpStatus.BAD_REQUEST, "게시글 등록에 실패하였습니다."),
    POST_NOT_UPDATED(HttpStatus.BAD_REQUEST, "작성자와 수정자가 달라서 게시글 수정에 실패하였습니다."),
    DELETE_POST_FAILED(HttpStatus.BAD_REQUEST, "작성자와 수정자가 달라서 게시글 삭제에 실패하였습니다."),
    COMMENT_NOT_CREATED(HttpStatus.BAD_REQUEST, "댓글 등록에 실패하였습니다."),
    COMMENT_NOT_UPDATED(HttpStatus.BAD_REQUEST, "작성자와 수정자가 달라서 댓글 수정에 실패하였습니다."),
    COMMENT_NOT_FOUND(HttpStatus.BAD_REQUEST, "존재하지 않는 댓글입니다."),
    DELETE_COMMENT_FAILED(HttpStatus.BAD_REQUEST, "작성자와 수정자가 달라서 댓글 삭제에 실패하였습니다."),
    FILE_SIZE_OVER_10MB(HttpStatus.BAD_REQUEST, "이미지의 용량이 10MB를 초과하였습니다."),
    CONTENT_EMPTY(HttpStatus.BAD_REQUEST, "컨텐츠가 비어있습니다."),
    URI_NOT_FOUND(HttpStatus.BAD_REQUEST, "잘못된 URI 요청입니다"),
    ;

    public final HttpStatus httpStatus;
    public final String message;
}

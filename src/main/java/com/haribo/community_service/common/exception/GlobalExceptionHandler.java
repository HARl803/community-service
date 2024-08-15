package com.haribo.community_service.common.exception;

import jakarta.validation.ConstraintViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

import java.net.URISyntaxException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<Object> handleCustomException(CustomException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(MaxUploadSizeExceededException.class)
    public ResponseEntity<Object> handleMaxUploadSizeExceededException(MaxSizeExceededException e) {
        ErrorCode errorCode = e.getErrorCode();
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException e) {
        ErrorCode errorCode = CustomErrorCode.CONTENT_EMPTY;
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(HttpClientErrorException.class)
    public ResponseEntity<Object> handleUnauthorizedException(HttpClientErrorException e) {
        ErrorCode errorCode = CustomErrorCode.USER_NOT_FOUND;
        return handleExceptionInternal(errorCode);
    }

    @ExceptionHandler(URISyntaxException.class)
    public ResponseEntity<Object> handleUnauthorizedException(URISyntaxException e) {
        ErrorCode errorCode = CustomErrorCode.URI_NOT_FOUND;
        return handleExceptionInternal(errorCode);
    }


    private ResponseEntity<Object> handleExceptionInternal(ErrorCode errorCode) {
        return ResponseEntity.status(errorCode.getHttpStatus())
                .body(makeErrorResponse(errorCode));
    }

    private ErrorResponse makeErrorResponse(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .customErrorCode(errorCode.name())
                .message(errorCode.getMessage())
                .build();
    }
}

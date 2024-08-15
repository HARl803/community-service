package com.haribo.community_service.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class MaxSizeExceededException extends RuntimeException {
    private final ErrorCode errorCode;
}

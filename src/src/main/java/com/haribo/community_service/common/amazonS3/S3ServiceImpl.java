package com.haribo.community_service.common.amazonS3;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.haribo.community_service.common.exception.CustomErrorCode;
import com.haribo.community_service.common.exception.CustomException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Slf4j
@Service
@RequiredArgsConstructor
public class S3ServiceImpl implements S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket-name}")
    private String bucketName;

    public String uploadFile(MultipartFile file) {

        log.info("S3ServiceImpl의 uploadFile 메서드 실행 - file이 null이거나, file이 비어있으면 null 반환!");

        if(file == null || file.isEmpty()) return null;

        if(file.getSize() > 10485760) throw new CustomException(CustomErrorCode.FILE_SIZE_OVER_10MB);

        String fileName = System.currentTimeMillis() + "_" + file.getOriginalFilename();
        try {
            amazonS3.putObject(new PutObjectRequest(bucketName, fileName, file.getInputStream(), null));
        } catch (IOException e) {
            throw new RuntimeException("Failed to upload file", e);
        }
        return amazonS3.getUrl(bucketName, fileName).toString();
    }
}

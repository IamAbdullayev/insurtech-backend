package com.insurtech.backend.service.impl;

import com.insurtech.backend.service.StorageService;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;

@Service
@RequiredArgsConstructor
public class S3StorageServiceImpl implements StorageService {
    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    public void upload(String key, InputStream data, String contentType) {
        ObjectMetadata metadata = ObjectMetadata.builder()
                .contentType(contentType)
                .build();
        s3Template.upload(bucketName, key, data, metadata);
    }

    public InputStream download(String key) {
        try {
            return s3Template.download(bucketName, key)
                    .getInputStream();
        } catch (IOException e) {
            throw new RuntimeException("Error when converting S3Resource to InputStream");
        }
    }

    public void delete(String key) {
        s3Template.deleteObject(bucketName, key);
    }

    public String getPresignedUrl(String key) {
        return s3Template.createSignedGetURL(bucketName, key, Duration.ofMinutes(45)).toString();
    }
}

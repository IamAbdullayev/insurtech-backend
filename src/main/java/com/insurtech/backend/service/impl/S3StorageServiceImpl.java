package com.insurtech.backend.service.impl;

import com.insurtech.backend.exception.S3DownloadException;
import com.insurtech.backend.exception.S3UploadException;
import com.insurtech.backend.service.StorageService;
import io.awspring.cloud.s3.ObjectMetadata;
import io.awspring.cloud.s3.S3Exception;
import io.awspring.cloud.s3.S3Template;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Objects;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3StorageServiceImpl implements StorageService {
    private final S3Template s3Template;

    @Value("${spring.cloud.aws.s3.bucket}")
    private String bucketName;

    public void upload(Long claimNumber, MultipartFile file) {
        String s3Key = generateKey(claimNumber, file);
        ObjectMetadata metadata = ObjectMetadata.builder()
                .contentType(file.getContentType())
                .contentLength(file.getSize())
                .build();
        try {
            s3Template.upload(bucketName, s3Key, file.getInputStream(), metadata);
        } catch (IOException e) {
            throw new S3UploadException("Failed to read file. fileName: " + file.getOriginalFilename(), e);
        } catch (Exception e) {
            throw new S3UploadException("Failed to upload file. fileName: " + file.getOriginalFilename(), e);
        }
    }

    public InputStream download(String key) {
        try {
            return s3Template.download(bucketName, key)
                    .getInputStream();
        } catch (IOException e) {
            throw new S3DownloadException("Failed to read file. key: " + key, e);
        } catch (S3Exception e) {
             throw new S3DownloadException("Failed to download file. key: " + key, e);
        }
    }

    public void delete(String key) {
        try {
            s3Template.deleteObject(bucketName, key);
        } catch (S3Exception e) {
            throw new S3Exception("Failed to delete file. key: " + key, e);
        }
    }

    public String getPresignedUrl(String key) {
        try {
            return s3Template.createSignedGetURL(bucketName, key, Duration.ofMinutes(45)).toString();
        } catch (S3Exception e) {
            throw new S3Exception("Failed to generate presignedUrl. key: " + key, e);
        }
    }

    private String generateKey(Long claimNumber, MultipartFile file) {
        String extension = getExtension(file.getOriginalFilename());
        String uniqueId = UUID.randomUUID().toString();

        // claims/{claimNumber}/{uniqueId}.{ext}
        return String.format("claims/%s/%s.%s", claimNumber, uniqueId, extension);
    }

    private String getExtension(String originalFileName) {
        if (Objects.isNull(originalFileName) || !originalFileName.contains(".")) return "bin";
        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
    }
}

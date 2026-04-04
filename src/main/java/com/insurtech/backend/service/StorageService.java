package com.insurtech.backend.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

public interface StorageService {

    void upload(Long claimNumber, MultipartFile file);

    InputStream download(String key);

    void delete(String key);

    String getPresignedUrl(String key);

}

package com.insurtech.backend.service;

import java.io.InputStream;

public interface StorageService {

    void upload(String key, InputStream data, String contentType);

    InputStream download(String key);

    void delete(String key);

    String getPresignedUrl(String key);

}

package com.insurtech.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ai")
public record AIAnalysisProperties(
    String baseUrl, int connectTimeoutSeconds, int readTimeoutMinutes) {}

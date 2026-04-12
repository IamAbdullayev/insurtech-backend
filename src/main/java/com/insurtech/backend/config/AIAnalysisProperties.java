package com.insurtech.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "ai")
public record AIAnalysisProperties(
    String baseUrl, String analyzeUri, int connectTimeoutSeconds, int readTimeoutMinutes) {}

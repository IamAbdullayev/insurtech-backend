package com.insurtech.backend.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "async")
public record AsyncProperties(int corePoolSize, int maxPoolSize, int queueCapacity) {}

package com.insurtech.backend.dto.ai.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record AIAnalysisRequest(
        @JsonProperty("image_urls") List<String> links) {}

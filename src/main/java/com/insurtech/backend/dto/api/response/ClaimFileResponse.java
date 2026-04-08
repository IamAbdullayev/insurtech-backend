package com.insurtech.backend.dto.api.response;

import com.insurtech.backend.domain.enums.ClaimFileStatus;
import com.insurtech.backend.domain.enums.ClaimFileType;
import java.time.Instant;

public record ClaimFileResponse(
    ClaimFileType type,
    String fileKey,
    String originalFilename,
    Long size,
    String contentType,
    ClaimFileStatus status,
    Instant uploadedAt) {}

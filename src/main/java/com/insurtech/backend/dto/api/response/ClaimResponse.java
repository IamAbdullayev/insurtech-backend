package com.insurtech.backend.dto.api.response;

import java.time.Instant;

public record ClaimResponse(
        long claimNumber,
        String accidentType,
        String location,
        String description,
        String status,
        Instant createdAt
) {}

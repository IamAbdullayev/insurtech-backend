package com.insurtech.backend.dto.api.response;

import com.insurtech.backend.domain.enums.ClaimEstimationStatus;
import java.math.BigDecimal;
import lombok.Builder;

@Builder
public record ClaimEstimationResponse(
    Double aiConfidence,
    BigDecimal estimatedCost,
    String rawResponse,
    ClaimEstimationStatus status) {}

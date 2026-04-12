package com.insurtech.backend.dto.ai.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.insurtech.backend.dto.ai.DamagePartDto;
import java.math.BigDecimal;
import java.util.List;

public record AIAnalysisResponse(
    @JsonProperty("damaged_parts") List<DamagePartDto> damagedParts,
    @JsonProperty("overall_summary") String overallSummary,
    @JsonProperty("estimated_repair_cost") BigDecimal estimatedRepairCost,
    @JsonProperty("confidence_score") Double confidenceScore) {}

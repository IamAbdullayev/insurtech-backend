package com.insurtech.backend.dto.ai;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;

public record DamagePartDto(
    @JsonProperty("part") String part,
    @JsonProperty("damage_description") String damageDescription,
    @JsonProperty("repair_decision") String repairDecision,
    @JsonProperty("repair_cost") BigDecimal repairCost) {}

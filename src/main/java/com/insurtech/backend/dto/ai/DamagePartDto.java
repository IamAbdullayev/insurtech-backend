package com.insurtech.backend.dto.ai;

import com.fasterxml.jackson.annotation.JsonProperty;

public record DamagePartDto(
    @JsonProperty("name") String name,
    @JsonProperty("damage_type") String damageType,
    @JsonProperty("repair_action") String repairAction) {}

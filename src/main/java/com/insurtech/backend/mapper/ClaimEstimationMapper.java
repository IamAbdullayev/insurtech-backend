package com.insurtech.backend.mapper;

import com.insurtech.backend.domain.entity.ClaimEstimation;
import com.insurtech.backend.dto.api.response.ClaimEstimationResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClaimEstimationMapper {
  ClaimEstimationResponse toResponse(ClaimEstimation claimEstimation);
}

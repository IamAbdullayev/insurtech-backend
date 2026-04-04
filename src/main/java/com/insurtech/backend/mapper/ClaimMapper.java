package com.insurtech.backend.mapper;

import com.insurtech.backend.domain.entity.Claim;
import com.insurtech.backend.dto.api.request.ClaimRequest;
import com.insurtech.backend.dto.api.response.ClaimResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClaimMapper {

    ClaimResponse toResponse(Claim claim);

    Claim toEntity(ClaimRequest claimRequest);
}

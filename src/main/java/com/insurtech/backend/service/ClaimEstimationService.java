package com.insurtech.backend.service;

import com.insurtech.backend.domain.entity.Claim;
import com.insurtech.backend.dto.api.response.ClaimEstimationResponse;
import java.util.UUID;

public interface ClaimEstimationService {

  ClaimEstimationResponse getByClaimNumber(String claimNumber);

  void estimate(UUID claimId);

  void delete(Claim claim);
}

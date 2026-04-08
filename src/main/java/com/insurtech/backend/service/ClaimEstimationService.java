package com.insurtech.backend.service;

import java.util.UUID;

public interface ClaimEstimationService {
  void estimate(UUID claimId);
}

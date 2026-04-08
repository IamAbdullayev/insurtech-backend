package com.insurtech.backend.service.impl;

import com.insurtech.backend.domain.enums.ClaimStatus;
import com.insurtech.backend.dto.api.response.ClaimFileResponse;
import com.insurtech.backend.dto.api.response.ClaimResponse;
import com.insurtech.backend.repository.ClaimEstimationRepository;
import com.insurtech.backend.service.ClaimEstimationService;
import com.insurtech.backend.service.ClaimFileService;
import com.insurtech.backend.service.ClaimService;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/* ApplicationEvent - when claim is created, publish the event
 * @Async + @TransactionalEventListener - listening events, if event came, will trigger the estimation method
 * scheduled retry - for any cases, will check db and find the claims with the status PENDING, call estimation method again
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ClaimEstimationServiceImpl implements ClaimEstimationService {

  private final ClaimEstimationRepository claimEstimationRepository;
  private final ClaimService claimService;
  private final ClaimFileService claimFileService;

  @Override
  public void estimate(UUID claimId) {
    ClaimResponse claim;

    try {
      claim = claimService.getById(claimId);
    } catch (Exception e) {
      log.warn("ESTIMATION_SKIPPED! Error occurred when getting claim: {}", e.getMessage());
      return;
    }

    if (claim.status() != ClaimStatus.SUBMITTED) {
      log.warn("ESTIMATION_SKIPPED! Claim estimation already done or in processing");
      return;
    }

    List<ClaimFileResponse> claimFileResponses = claimFileService.getByClaimId(claimId);
  }
}

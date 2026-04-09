package com.insurtech.backend.service.impl;

import com.insurtech.backend.domain.entity.Claim;
import com.insurtech.backend.domain.entity.ClaimEstimation;
import com.insurtech.backend.domain.enums.ClaimEstimationStatus;
import com.insurtech.backend.domain.enums.ClaimStatus;
import com.insurtech.backend.dto.api.response.ClaimFileResponse;
import com.insurtech.backend.repository.ClaimEstimationRepository;
import com.insurtech.backend.repository.ClaimRepository;
import com.insurtech.backend.service.ClaimEstimationService;
import com.insurtech.backend.service.ClaimFileService;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/* ApplicationEvent - when claim is created, publish the event
 * @Async + @TransactionalEventListener - listening events, if event came, will trigger the estimation method
 * scheduled retry - for any cases, will check db and find the claims with the status PENDING, call estimation method again
 */

@Slf4j
@Service
@RequiredArgsConstructor
public class ClaimEstimationServiceImpl implements ClaimEstimationService {

  private final ClaimEstimationRepository claimEstimationRepository;
  private final ClaimRepository claimRepository;
  private final ClaimFileService claimFileService;

  @Override
  @Transactional
  public void estimate(UUID claimId) {
    Claim claim = claimRepository.findById(claimId).orElse(null);

    if (Objects.isNull(claim)) {
      log.warn("ESTIMATION_SKIPPED! Error occurred when getting claim");
      return;
    }

    if (claim.getStatus() != ClaimStatus.SUBMITTED) {
      log.warn("ESTIMATION_SKIPPED! Claim estimation already done or in processing");
      return;
    }

    List<ClaimFileResponse> claimFileResponses = claimFileService.getByClaimId(claimId);

    log.warn("!!!!!!!!!!!! ESTIMATION PROCESS STARTED !!!!!!!!!!!!");
    claimFileResponses.forEach(
        file ->
            log.info(
                "FILE_KEY: {} \n| FILE_TYPE: {} \n| FILE_NAME: {} \n| SIZE: {} \n| CONTENT_TYPE: {} \n| STATUS: {} \n| UPLOADED_AT: {}",
                file.fileKey(),
                file.type(),
                file.originalFilename(),
                file.size(),
                file.contentType(),
                file.status(),
                file.uploadedAt()));
    log.warn("!!!!!!!!!!!! ESTIMATION PROCESS FINISHED !!!!!!!!!!!!");

    ClaimEstimation.ClaimEstimationBuilder response =
        ClaimEstimation.builder()
            .claim(claim)
            .estimatedCost(BigDecimal.valueOf(1005))
            .aiConfidence(89D)
            .rawResponse("")
            .status(ClaimEstimationStatus.ESTIMATED);

    claimEstimationRepository.save(response.build());
  }
}

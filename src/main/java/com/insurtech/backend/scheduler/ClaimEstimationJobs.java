package com.insurtech.backend.scheduler;

import com.insurtech.backend.domain.entity.ClaimEstimation;
import com.insurtech.backend.domain.enums.ClaimEstimationStatus;
import com.insurtech.backend.repository.ClaimEstimationRepository;
import com.insurtech.backend.service.ClaimEstimationService;
import java.time.Duration;
import java.time.Instant;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClaimEstimationJobs {
  private final ClaimEstimationRepository claimEstimationRepository;
  private final ClaimEstimationService claimEstimationService;

  @Value("${ai.jobs.max-attempt}")
  private int maxAttempt;

  @Value("${ai.read-timeout-minutes}")
  private int maxEstimationTime;

  @Scheduled(cron = "${ai.jobs.claim-estimation-retry}")
  public void retryFailed() {
    Pageable limit = PageRequest.of(0, 10);

    List<ClaimEstimation> jobs =
        claimEstimationRepository.findRetryable(
            ClaimEstimationStatus.ESTIMATION_FAILED, maxAttempt, limit);

    for (ClaimEstimation job : jobs) {
      try {
        claimEstimationService.estimate(job.getClaim().getId());
        log.info("ESTIMATION_RETRY | estimationId: {}", job.getId());
      } catch (Exception ex) {
        log.warn(
            "ESTIMATION_RETRY_ERROR | estimationId: {} | attemptCount: {} | error: {}",
            job.getId(),
            job.getAttemptCount(),
            ex.getMessage());
      }
    }
  }

  @Scheduled(cron = "${ai.jobs.stuck-estimation-recover}")
  @Transactional
  public void recoverStuckEstimations() {
    Pageable limit = PageRequest.of(0, 10);
    Instant stuckThreshold = Instant.now().minus(Duration.ofMinutes(maxEstimationTime));

    List<ClaimEstimation> jobs =
        claimEstimationRepository.findStuckEstimations(
            List.of(ClaimEstimationStatus.ESTIMATING, ClaimEstimationStatus.PENDING),
            stuckThreshold,
            limit);

    for (ClaimEstimation job : jobs) {
      job.setStatus(ClaimEstimationStatus.ESTIMATION_FAILED);
      claimEstimationRepository.save(job);
      log.info(
          "ESTIMATION_STUCK_JOB_RECOVERED | {} | updatedAt: {}", job.getId(), job.getUpdatedAt());
    }
  }
}

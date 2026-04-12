package com.insurtech.backend.listener;

import com.insurtech.backend.event.ClaimCreatedEvent;
import com.insurtech.backend.service.ClaimEstimationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Slf4j
@Component
@RequiredArgsConstructor
public class EventListener {
  private final ClaimEstimationService claimEstimationService;

  @Async("estimationExecutor")
  @TransactionalEventListener
  public void onClaimCreated(ClaimCreatedEvent event) {
    try {
      log.info("ESTIMATION_ON_CLAIM_CREAT_STARTED | claimId: {}", event.claimId());
      claimEstimationService.estimate(event.claimId());
      log.info("ESTIMATION_ON_CLAIM_CREAT_FINISHED | claimId: {}", event.claimId());
    } catch (Exception ex) {
      log.warn(
          "ESTIMATION_ON_CLAIM_CREAT_ERROR | claimId: {} | error: {}",
          event.claimId(),
          ex.getMessage());
    }
  }
}

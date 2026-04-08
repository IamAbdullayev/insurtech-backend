package com.insurtech.backend.listener;

import com.insurtech.backend.event.ClaimCreatedEvent;
import com.insurtech.backend.service.ClaimEstimationService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

@Component
@RequiredArgsConstructor
public class EventListener {
  private final ClaimEstimationService claimEstimationService;

  @Async("estimationExecutor")
  @TransactionalEventListener
  public void onClaimCreated(ClaimCreatedEvent event) {
    claimEstimationService.estimate(event.claimId());
  }
}

package com.insurtech.backend.scheduler;

import com.insurtech.backend.config.AuthProperties;
import com.insurtech.backend.repository.RefreshTokenRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenCleanUpTasks {
  private final RefreshTokenRepository refreshTokenRepository;
  private final AuthProperties authProperties;

  @Scheduled(cron = "${auth.task.refresh-token-clean-up}")
  @Transactional
  public void RefreshTokenCleanUp() {
    int deleted =
        refreshTokenRepository.deleteExpiredBefore(
            Instant.now().minus(authProperties.jwt().refreshTokenTtlDays(), ChronoUnit.DAYS));
    log.info("Token cleanup: removed {} expired tokens", deleted);
  }
}

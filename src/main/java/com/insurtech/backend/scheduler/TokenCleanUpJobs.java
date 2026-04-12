package com.insurtech.backend.scheduler;

import com.insurtech.backend.repository.RefreshTokenRepository;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Component
@RequiredArgsConstructor
public class TokenCleanUpJobs {

  private final RefreshTokenRepository refreshTokenRepository;

  @Value("${auth.jwt.refresh-token-ttl-days}")
  private int refreshTokenTtlDays;

  @Scheduled(cron = "${auth.task.refresh-token-clean-up}")
  @Transactional
  public void RefreshTokenCleanUp() {
    int deleted =
        refreshTokenRepository.deleteExpiredBefore(
            Instant.now().minus(refreshTokenTtlDays, ChronoUnit.DAYS));
    log.info("Token cleanup: removed {} expired tokens", deleted);
  }
}

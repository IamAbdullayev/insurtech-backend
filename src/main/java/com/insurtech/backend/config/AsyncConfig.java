package com.insurtech.backend.config;

import java.util.concurrent.Executor;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

@Configuration
@RequiredArgsConstructor
public class AsyncConfig {
  private final AsyncProperties asyncProperties;

  @Bean("estimationExecutor")
  public Executor estimationExecutor() {
    ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
    executor.setThreadNamePrefix("estimation-");
    executor.setCorePoolSize(asyncProperties.corePoolSize());
    executor.setMaxPoolSize(asyncProperties.maxPoolSize());
    executor.setQueueCapacity(asyncProperties.queueCapacity());
    executor.initialize();
    return executor;
  }
}

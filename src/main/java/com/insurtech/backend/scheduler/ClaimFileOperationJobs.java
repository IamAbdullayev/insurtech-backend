package com.insurtech.backend.scheduler;

import com.insurtech.backend.repository.ClaimFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClaimFileOperationJobs {
  private final ClaimFileRepository claimFileRepository;
}

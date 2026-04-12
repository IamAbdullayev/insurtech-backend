package com.insurtech.backend.service.tx;

import com.insurtech.backend.repository.ClaimFileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClaimFileTxService {
  private final ClaimFileRepository claimFileRepository;
}

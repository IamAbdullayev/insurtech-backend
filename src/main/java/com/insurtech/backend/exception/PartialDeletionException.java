package com.insurtech.backend.exception;

import java.util.List;
import java.util.UUID;
import lombok.Getter;

@Getter
public class PartialDeletionException extends RuntimeException {

  private final List<UUID> failed;

  public PartialDeletionException(List<UUID> failed, String message) {
    super(message);
    this.failed = failed;
  }
}

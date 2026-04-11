package com.insurtech.backend.exception;

import com.insurtech.backend.exception.handler.ErrorCode;
import lombok.Getter;

@Getter
public class AIServiceException extends RuntimeException {
  private final ErrorCode errorCode;

  public AIServiceException(ErrorCode errorCode) {
    super(errorCode.getDescription());
    this.errorCode = errorCode;
  }

  public AIServiceException(ErrorCode errorCode, String message) {
    super(message);
    this.errorCode = errorCode;
  }

  public AIServiceException(ErrorCode errorCode, String message, Throwable cause) {
    super(message, cause);
    this.errorCode = errorCode;
  }
}

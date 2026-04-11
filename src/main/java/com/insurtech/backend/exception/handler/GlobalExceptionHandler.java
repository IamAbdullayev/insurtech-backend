package com.insurtech.backend.exception.handler;

import com.insurtech.backend.exception.AIServiceException;
import com.insurtech.backend.exception.AuthException;
import com.insurtech.backend.exception.InvalidValueException;
import com.insurtech.backend.exception.NotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import java.util.stream.Collectors;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(AuthException.class)
  public ResponseEntity<ErrorBody> handle(AuthException ex, HttpServletRequest req) {
    ErrorCode errorCode = ex.getErrorCode();
    String message = ex.getMessage() != null ? ex.getMessage() : errorCode.getDescription();

    HttpStatus status =
        switch (errorCode) {
          case INVALID_CREDENTIALS,
              TOKEN_EXPIRED,
              TOKEN_INVALID,
              TOKEN_REVOKED,
              TOKEN_REUSE_DETECTED ->
              HttpStatus.UNAUTHORIZED;
          case ACCOUNT_DISABLED, FORBIDDEN -> HttpStatus.FORBIDDEN;
          case REGISTRATION_FAILED -> HttpStatus.CONFLICT;
          default -> HttpStatus.BAD_REQUEST;
        };

    if (errorCode == ErrorCode.TOKEN_REUSE_DETECTED) {
      log.warn("Token reuse detected: {}", message);
    }

    return ResponseEntity.status(status)
        .body(ErrorBody.of(status.value(), errorCode.name(), message, req.getRequestURI()));
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<ErrorBody> handle(
      MethodArgumentNotValidException ex, HttpServletRequest req) {
    String msg =
        ex.getBindingResult().getFieldErrors().stream()
            .map(er -> er.getField() + ": " + er.getDefaultMessage())
            .collect(Collectors.joining("; "));

    return ResponseEntity.badRequest()
        .body(
            ErrorBody.of(
                HttpStatus.BAD_REQUEST.value(),
                ErrorCode.VALIDATION_ERROR.name(),
                msg,
                req.getRequestURI()));
  }

  @ExceptionHandler(NotFoundException.class)
  public ResponseEntity<ErrorBody> handle(NotFoundException ex, HttpServletRequest req) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            ErrorBody.of(
                HttpStatus.NOT_FOUND.value(),
                ex.getErrorCode().name(),
                ex.getMessage() != null ? ex.getMessage() : ex.getErrorCode().getDescription(),
                req.getRequestURI()));
  }

  @ExceptionHandler(InvalidValueException.class)
  public ResponseEntity<ErrorBody> handle(InvalidValueException ex, HttpServletRequest req) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            ErrorBody.of(
                HttpStatus.BAD_REQUEST.value(),
                ex.getErrorCode().name(),
                ex.getMessage() != null ? ex.getMessage() : ex.getErrorCode().getDescription(),
                req.getRequestURI()));
  }

  @ExceptionHandler(AIServiceException.class)
  public ResponseEntity<ErrorBody> handle(AIServiceException ex, HttpServletRequest req) {
    log.error("AI service error. errorCode: {} | message: {}", ex.getErrorCode(), ex.getMessage());
    return ResponseEntity.status(HttpStatus.BAD_GATEWAY)
        .body(
            ErrorBody.of(
                HttpStatus.BAD_GATEWAY.value(),
                ex.getErrorCode().name(),
                ex.getMessage() != null ? ex.getMessage() : ex.getErrorCode().getDescription(),
                req.getRequestURI()));
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<ErrorBody> handle(Exception ex, HttpServletRequest req) {
    log.error("Unhandled exception", ex);
    return ResponseEntity.internalServerError()
        .body(
            ErrorBody.of(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                ErrorCode.INTERNAL_ERROR.name(),
                ErrorCode.INTERNAL_ERROR.getDescription(),
                req.getRequestURI()));
  }
}

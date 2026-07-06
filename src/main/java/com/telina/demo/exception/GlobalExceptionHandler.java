package com.telina.demo.exception;

import java.time.Instant;
import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(ResourceNotFoundException.class)
  public ResponseEntity<Map<String, Object>> handleNotFound(ResourceNotFoundException ex) {
    return ResponseEntity.status(HttpStatus.NOT_FOUND)
        .body(
            Map.of(
                "error", "Not Found",
                "message", ex.getMessage(),
                "timestamp", Instant.now().toString()));
  }

  @ExceptionHandler(InvalidUuidException.class)
  public ResponseEntity<Map<String, Object>> handleInvalidUuid(InvalidUuidException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            Map.of(
                "error", "Bad Request",
                "message", ex.getMessage(),
                "timestamp", Instant.now().toString()));
  }

  @ExceptionHandler(IllegalArgumentException.class)
  public ResponseEntity<Map<String, Object>> handleIllegalArgument(IllegalArgumentException ex) {
    return ResponseEntity.status(HttpStatus.BAD_REQUEST)
        .body(
            Map.of(
                "error", "Bad Request",
                "message", ex.getMessage(),
                "timestamp", Instant.now().toString()));
  }
}

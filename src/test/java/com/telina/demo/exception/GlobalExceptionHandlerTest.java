package com.telina.demo.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Map;
import java.util.UUID;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class GlobalExceptionHandlerTest {

  private final GlobalExceptionHandler handler = new GlobalExceptionHandler();

  @Test
  void handleNotFound_shouldReturn404WithMessage() {
    UUID id = UUID.randomUUID();
    ResourceNotFoundException exception = new ResourceNotFoundException("Book", id);

    ResponseEntity<Map<String, Object>> response = handler.handleNotFound(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
    assertThat(response.getBody()).containsEntry("error", "Not Found");
    assertThat(response.getBody()).containsEntry("message", exception.getMessage());
    assertThat(response.getBody()).containsKey("timestamp");
  }

  @Test
  void handleInvalidUuid_shouldReturn400WithMessage() {
    InvalidUuidException exception = new InvalidUuidException("bad-id");

    ResponseEntity<Map<String, Object>> response = handler.handleInvalidUuid(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).containsEntry("error", "Bad Request");
    assertThat(response.getBody()).containsEntry("message", exception.getMessage());
  }

  @Test
  void handleIllegalArgument_shouldReturn400WithMessage() {
    IllegalArgumentException exception = new IllegalArgumentException("bad argument");

    ResponseEntity<Map<String, Object>> response = handler.handleIllegalArgument(exception);

    assertThat(response.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
    assertThat(response.getBody()).containsEntry("error", "Bad Request");
    assertThat(response.getBody()).containsEntry("message", "bad argument");
  }
}

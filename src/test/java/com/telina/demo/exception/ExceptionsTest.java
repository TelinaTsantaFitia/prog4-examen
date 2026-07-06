package com.telina.demo.exception;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.UUID;
import org.junit.jupiter.api.Test;

class ExceptionsTest {

  @Test
  void resourceNotFoundException_shouldBuildExpectedMessage() {
    UUID id = UUID.randomUUID();

    ResourceNotFoundException exception = new ResourceNotFoundException("Book", id);

    assertThat(exception.getMessage()).isEqualTo("Book not found with identifier: " + id);
  }

  @Test
  void invalidUuidException_shouldBuildExpectedMessage() {
    InvalidUuidException exception = new InvalidUuidException("not-a-uuid");

    assertThat(exception.getMessage()).isEqualTo("Invalid UUID: not-a-uuid");
  }
}

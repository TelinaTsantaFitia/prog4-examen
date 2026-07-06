package com.example.demo.exception;

public class InvalidUuidException extends RuntimeException {
  public InvalidUuidException(String value) {
    super("Invalid UUID: " + value);
  }
}

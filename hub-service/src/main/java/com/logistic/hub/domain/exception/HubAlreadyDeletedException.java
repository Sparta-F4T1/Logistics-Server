package com.logistic.hub.domain.exception;

public class HubAlreadyDeletedException extends RuntimeException {
  public HubAlreadyDeletedException(String message) {
    super(message);
  }
}

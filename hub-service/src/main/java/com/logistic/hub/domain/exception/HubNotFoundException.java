package com.logistic.hub.domain.exception;

public class HubNotFoundException extends RuntimeException {
  public HubNotFoundException(String message) {
    super(message);
  }
}

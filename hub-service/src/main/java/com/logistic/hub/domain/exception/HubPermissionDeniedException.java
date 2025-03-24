package com.logistic.hub.domain.exception;

public class HubPermissionDeniedException extends RuntimeException {
  public HubPermissionDeniedException(String message) {
    super(message);
  }
}

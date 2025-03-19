package com.logistic.hub.domain.exception;

public class RouteAlreadyDeletedException extends RuntimeException {
  public RouteAlreadyDeletedException(String message) {
    super(message);
  }
}

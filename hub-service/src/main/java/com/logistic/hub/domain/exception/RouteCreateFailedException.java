package com.logistic.hub.domain.exception;

public class RouteCreateFailedException extends RuntimeException {
  public RouteCreateFailedException(String message) {
    super(message);
  }
}

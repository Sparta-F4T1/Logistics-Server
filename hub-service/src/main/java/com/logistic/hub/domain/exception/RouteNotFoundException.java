package com.logistic.hub.domain.exception;

public class RouteNotFoundException extends RuntimeException {
  public RouteNotFoundException(String message) {
    super(message);
  }
}

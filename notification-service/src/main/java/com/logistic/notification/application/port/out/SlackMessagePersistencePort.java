package com.logistic.notification.application.port.out;

public interface SlackMessagePersistencePort {
  void create(String driverID, String message);
}

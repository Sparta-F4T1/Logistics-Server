package com.logistic.ai.application.port.in.command;

import java.time.LocalDateTime;

public record GetDeadLineCommand(
    LocalDateTime orderCreatedAt,
    Integer totalHubDeliveryTime,
    String arrivalHubAddress,
    String arrivalCompanyAddress
) {
  @Override
  public String toString() {
    return "GetDeadLineCommand{" +
        "orderCreatedAt=" + orderCreatedAt +
        ", totalHubDeliveryTime=" + totalHubDeliveryTime +
        ", arrivalHubAddress='" + arrivalHubAddress + '\'' +
        ", arrivalCompanyAddress='" + arrivalCompanyAddress + '\'' +
        '}';
  }
}
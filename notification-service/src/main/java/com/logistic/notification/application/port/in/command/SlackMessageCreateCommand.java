package com.logistic.notification.application.port.in.command;

import java.time.LocalDateTime;
import java.util.List;

public record SlackMessageCreateCommand(
    Long orderId,
    String userName,
    String slackId,
    String productName,
    Integer quantity,
    List<String> hubNames,
    String CompanyAddress,
    String driverName,
    String driver,
    String driverId,
    LocalDateTime deliveryDeadLine
) {
}

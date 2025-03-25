package com.logistic.notification.adapter.in.message;

import java.time.LocalDateTime;
import java.util.List;

public record CreateDeliveryEvent(
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

package com.logistic.common.internal.request;

import java.time.LocalDateTime;

public record GetDeadLineRequest(
    LocalDateTime orderCreatedAt,
    Integer totalHubDeliveryTime,
    String arrivalHubAddress,
    String arrivalCompanyAddress) {
}

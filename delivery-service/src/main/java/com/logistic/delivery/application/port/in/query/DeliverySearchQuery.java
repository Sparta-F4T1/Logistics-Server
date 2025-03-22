package com.logistic.delivery.application.port.in.query;

import java.util.Optional;

public record DeliverySearchQuery(
    Optional<Long> orderId,
    Optional<Long> departCompanyId,
    Optional<Long> arrivalCompanyId,
    Optional<String> driverId
) {
}

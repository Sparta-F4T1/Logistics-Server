package com.logistic.driver.domain.event;

import java.util.List;

public record AssignedDriverToDeliveryEvent(
    String driverId,
    List<Long> companyIds) {
}

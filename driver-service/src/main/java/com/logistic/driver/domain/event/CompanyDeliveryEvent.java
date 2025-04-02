package com.logistic.driver.domain.event;

import java.util.List;

public record CompanyDeliveryEvent(
    Long hubId,
    List<Long> companyIds) implements DomainEvent {
}

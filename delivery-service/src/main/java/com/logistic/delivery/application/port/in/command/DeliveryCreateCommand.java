package com.logistic.delivery.application.port.in.command;

import lombok.Builder;

@Builder
public record DeliveryCreateCommand(
    Long orderId,
    Long arrivalCompanyId,
    Long departCompanyId,
    Long departHubId,
    Long arrivalHubId
) {
}

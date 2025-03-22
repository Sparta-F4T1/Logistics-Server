package com.logistic.delivery.application.port.in.command;

import lombok.Builder;

@Builder
public record HubDeliveryHistoryUpdateCommand(
    Long departHubId,
    Long arrivalHubId,
    String status
) {
}

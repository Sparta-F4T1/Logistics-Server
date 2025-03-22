package com.logistic.delivery.application.port.in.command;

import java.util.Optional;
import lombok.Builder;

@Builder
public record DeliveryUpdateCommand(
    Optional<String> status,
    Optional<String> driverId
) {
}

package com.logistic.delivery.adaptor.in.web.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DeliveryCreateRequest(
    @NotNull Long orderId,
    @NotNull Long departHubId,
    @NotNull Long arrivalHubId,
    @NotNull String driverId
){
}

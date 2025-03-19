package com.logistic.delivery.adapter.in.web.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DeliveryCreateRequest(
    @NotNull Long orderId,
    @NotNull Long departCompanyId,
    @NotNull Long arrivalCompanyId
){
}

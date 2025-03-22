package com.logistic.delivery.adapter.in.web.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record HubDeliveryHistoryUpdateRequest(
    @NotNull Long departHubId,
    @NotNull Long arrivalHubId,
    @NotNull String status
){
}

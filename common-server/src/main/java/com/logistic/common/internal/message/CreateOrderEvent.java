package com.logistic.common.internal.message;

public record CreateOrderEvent(Long orderId, Long departCompanyId, Long arrivalCompanyId, Long departHubId,
                               Long arrivalHubId) {

}

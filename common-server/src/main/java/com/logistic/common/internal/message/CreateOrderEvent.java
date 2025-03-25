package com.logistic.common.internal.message;

public record CreateOrderEvent(
    Long orderId,
    String userId,
    String userName,
    String slackEmail,
    Long productId,
    String productName,
    int quantity,
    String memo,
    Long departCompanyId,
    Long arrivalCompanyId,
    Long departHubId,
    Long arrivalHubId
) {

}

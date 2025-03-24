package com.logistic.order.adapter.in.external.web.request;

import java.time.LocalDateTime;

public record SearchOrderRequest(
    Long sellerId,
    Long buyerId,
    LocalDateTime dateStart,
    LocalDateTime dateEnd
) {
}

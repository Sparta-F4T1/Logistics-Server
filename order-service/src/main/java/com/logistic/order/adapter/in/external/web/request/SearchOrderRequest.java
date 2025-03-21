package com.logistic.order.adapter.in.external.web.request;

import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;

public record SearchOrderRequest(
    Long sellerId,
    Long buyerId,
    Pageable pageable,
    LocalDateTime dateStart,
    LocalDateTime dateEnd
) {
}

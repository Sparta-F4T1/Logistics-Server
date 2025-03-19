package com.logistic.order.application.port.in.query;

import java.time.LocalDateTime;
import org.springframework.data.domain.Pageable;

public record SearchOrderQuery(
    Long sellerId,
    Long buyerId,
    Pageable pageable,
    LocalDateTime dateStart,
    LocalDateTime dateEnd
) {
}

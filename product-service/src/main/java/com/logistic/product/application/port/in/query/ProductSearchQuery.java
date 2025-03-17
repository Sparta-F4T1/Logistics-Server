package com.logistic.product.application.port.in.query;

import org.springframework.data.domain.Pageable;

public record ProductSearchQuery(Long companyId, String name, Pageable pageable) {
}

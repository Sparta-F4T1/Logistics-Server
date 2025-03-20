package com.logistic.product.application.port.in.query;

import com.logistic.common.passport.model.Passport;
import org.springframework.data.domain.Pageable;

public record SearchProductQuery(
    Long companyId,
    String name,
    Pageable pageable,
    Passport passport) {
}

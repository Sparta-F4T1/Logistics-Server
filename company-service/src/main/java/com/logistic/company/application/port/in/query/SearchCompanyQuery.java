package com.logistic.company.application.port.in.query;

import com.logistic.company.domain.model.CompanyType;
import org.springframework.data.domain.Pageable;

public record SearchCompanyQuery(
    Long hubId,
    String name,
    CompanyType type,
    Pageable pageable) {
}

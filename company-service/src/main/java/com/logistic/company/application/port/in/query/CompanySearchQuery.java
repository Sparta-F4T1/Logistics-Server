package com.logistic.company.application.port.in.query;

import com.logistic.company.domain.CompanyType;
import org.springframework.data.domain.Pageable;

public record CompanySearchQuery(
    Long hubId,
    String name,
    CompanyType type,
    Pageable pageable) {
}

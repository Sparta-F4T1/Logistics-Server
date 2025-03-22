package com.logistic.company.adapter.in.external.web.request;

import com.logistic.company.domain.model.CompanyType;

public record SearchCompanyRequest(
    Long hubId,
    String name,
    CompanyType type) {
}

package com.logistic.company.adaptor.in.web.request;

import com.logistic.company.domain.CompanyType;

public record CompanySearchRequest(
    Long hubId,
    String name,
    CompanyType type) {
}

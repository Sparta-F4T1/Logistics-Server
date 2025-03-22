package com.logistic.company.adapter.in.external.web.request;

import com.logistic.company.domain.model.CompanyType;
import java.util.List;

public record UpdateCompanyRequest(
    String name,
    CompanyType type,
    String road,
    Long hubId,
    List<String> userIds) {
}

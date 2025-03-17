package com.logistic.company.adaptor.in.web.response;

import com.logistic.company.domain.CompanyType;
import com.logistic.company.domain.vo.Address;

public record CompanyResponse(
    Long companyId,
    String name,
    CompanyType type,
    Address address,
    String manager,
    Long hubId,
    Boolean isDeleted) {
}
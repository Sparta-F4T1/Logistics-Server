package com.logistic.company.adaptor.in.web.request;

import com.logistic.company.domain.CompanyType;

public record CompanyCreateRequest(
    String name,
    CompanyType type,
    String address,
    String manager,
    Long hubId) {

}
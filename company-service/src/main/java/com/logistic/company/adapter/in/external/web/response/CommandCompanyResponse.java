package com.logistic.company.adapter.in.external.web.response;

import com.logistic.company.domain.model.CompanyType;
import com.logistic.company.domain.model.vo.Gps;
import com.logistic.company.domain.model.vo.User;
import java.util.List;

public record CommandCompanyResponse(
    Long companyId,
    String name,
    CompanyType type,
    Gps gps,
    Long hubId,
    List<User> managers
) {
}
package com.logistic.company.application.port.in.query;

import com.logistic.common.passport.model.Passport;
import java.util.List;

public record ListCompanyQuery(
    List<Long> companyIds,
    Passport passport) {
}

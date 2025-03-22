package com.logistic.company.application.port.in.query;

import com.logistic.common.passport.model.Passport;

public record FindCompanyQuery(
    Long companyId,
    Passport passport) {
}

package com.logistic.company.application.port.in.command;

import com.logistic.common.passport.model.Passport;

public record DeleteCompanyCommand(
    Long companyId,
    Passport passport) {
}

package com.logistic.driver.application.port.in.command;

import com.logistic.common.passport.model.Passport;
import java.util.List;

public record AssignCompanyDriversCommand(
    Long hubId,
    List<Long> companyIds,
    Passport passport) {
}

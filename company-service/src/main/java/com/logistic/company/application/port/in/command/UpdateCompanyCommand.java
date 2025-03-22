package com.logistic.company.application.port.in.command;

import com.logistic.common.passport.model.Passport;
import com.logistic.company.domain.command.CompanyForUpdate;
import com.logistic.company.domain.model.CompanyType;
import com.logistic.company.domain.model.vo.Gps;
import com.logistic.company.domain.model.vo.Hub;
import com.logistic.company.domain.model.vo.User;
import java.util.List;

public record UpdateCompanyCommand(
    Long companyId,
    String name,
    CompanyType type,
    String road,
    Long hubId,
    List<String> userIds,
    Passport passport) {

  public CompanyForUpdate toForUpdate(Hub hub, Gps gps, List<User> users) {
    return new CompanyForUpdate(
        companyId, name, type, hub, gps, users
    );
  }
}

package com.logistic.company.application.port.in.command;

import com.logistic.common.passport.model.Passport;
import com.logistic.company.domain.command.CompanyForCreate;
import com.logistic.company.domain.model.CompanyType;
import com.logistic.company.domain.model.vo.Gps;
import com.logistic.company.domain.model.vo.Hub;
import com.logistic.company.domain.model.vo.User;
import java.util.List;

public record CreateCompanyCommand(
    String name,
    CompanyType type,
    String road,
    Long hubId,
    List<String> userIds,
    Passport passport) {

  public CompanyForCreate toForCreate(Hub hub, Gps gps, List<User> userList) {
    return new CompanyForCreate(
        name, type, hub, gps, userList);
  }
}
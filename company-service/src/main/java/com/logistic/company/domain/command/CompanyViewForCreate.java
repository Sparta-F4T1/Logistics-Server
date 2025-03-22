package com.logistic.company.domain.command;

import com.logistic.company.domain.model.CompanyType;
import com.logistic.company.domain.model.vo.Gps;
import com.logistic.company.domain.model.vo.Hub;
import com.logistic.company.domain.model.vo.User;
import java.util.List;

public record CompanyViewForCreate(
    Long companyId,
    String name,
    CompanyType type,
    Hub hub,
    Gps gps,
    List<User> users) {
}

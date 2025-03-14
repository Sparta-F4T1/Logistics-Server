package com.logistic.company.application.port.in.command;

import com.logistic.company.domain.CompanyType;

public record CompanyCreateCommand(
    String name,
    CompanyType type,
    String address,
    String manager,
    Long hubId) {
}
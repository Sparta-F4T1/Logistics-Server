package com.logistic.company.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.company.application.port.in.CompanyUseCase;
import com.logistic.company.application.port.in.command.CompanyCreateCommand;
import com.logistic.company.application.port.out.CompanyPersistencePort;
import com.logistic.company.application.port.out.GpsClientPort;
import com.logistic.company.domain.Company;
import com.logistic.company.domain.vo.Address;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class CompanyService implements CompanyUseCase {
  private final CompanyPersistencePort companyPersistencePort;
  private final GpsClientPort gpsClientPort;

  @Override
  public Company createCompany(final CompanyCreateCommand command) {
    final Address address = gpsClientPort.getAddress(command.address());
    final Company company = Company.create(command.name(), command.type(), address, command.manager(), command.hubId());
    return companyPersistencePort.save(company);
  }
}
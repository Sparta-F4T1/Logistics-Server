package com.logistic.company.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.company.application.port.in.CompanyUseCase;
import com.logistic.company.application.port.in.command.CompanyCreateCommand;
import com.logistic.company.application.port.in.command.CompanyDeleteCommand;
import com.logistic.company.application.port.in.command.CompanyUpdateCommand;
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
    // todo 주소, 허브 검증 구현
    // todo 권한 검증
    final Address address = gpsClientPort.getAddress(command.address());
    final Company company = Company.create(command.name(), command.type(), address, command.manager(), command.hubId());
    return companyPersistencePort.save(company);
  }

  @Override
  public Company updateCompany(final CompanyUpdateCommand command) {
    // todo 주소, 허브 검증
    // todo 권한 검증
    Company company = findOrElseThrow(command.companyId());
    final Address address = gpsClientPort.getAddress(command.address());
    company.updateCompany(command.name(), command.type(), address, command.manager(), command.hubId());
    return companyPersistencePort.save(company);
  }

  @Override
  public void deleteCompany(final CompanyDeleteCommand command) {
    // todo 권한 검증
    Company company = findOrElseThrow(command.companyId());
    company.softDelete();
    companyPersistencePort.save(company);
  }

  private Company findOrElseThrow(final Long companyId) {
    // todo 예외처리 수정
    return companyPersistencePort.findById(companyId)
        .orElseThrow(() -> new RuntimeException("Company not found"));
  }
}
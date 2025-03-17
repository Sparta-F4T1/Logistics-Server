package com.logistic.company.application.service;

import com.logistic.company.application.port.in.CompanyQueryUseCase;
import com.logistic.company.application.port.in.query.CompanyFindQuery;
import com.logistic.company.application.port.in.query.CompanySearchQuery;
import com.logistic.company.application.port.out.CompanyPersistencePort;
import com.logistic.company.domain.Company;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompanyQueryService implements CompanyQueryUseCase {
  private final CompanyPersistencePort companyPersistencePort;

  public Company findCompany(final CompanyFindQuery query) {
    //todo 예외처리 구현
    return companyPersistencePort.findById(query.companyId()).orElseThrow(
        () -> new RuntimeException("예외처리 구현하기"));
  }

  public Page<Company> search(final CompanySearchQuery query) {
    return companyPersistencePort.saerch(query);
  }
}

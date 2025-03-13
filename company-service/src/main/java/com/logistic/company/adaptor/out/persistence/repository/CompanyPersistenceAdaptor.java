package com.logistic.company.adaptor.out.persistence.repository;

import com.logistic.common.annotation.PersistenceAdapter;
import com.logistic.company.adaptor.out.persistence.CompanyEntity;
import com.logistic.company.adaptor.out.persistence.mapper.CompanyPersistenceMapper;
import com.logistic.company.application.port.out.CompanyPersistencePort;
import com.logistic.company.domain.Company;
import lombok.RequiredArgsConstructor;

@PersistenceAdapter
@RequiredArgsConstructor
public class CompanyPersistenceAdaptor implements CompanyPersistencePort {
  private final CompanyJpaRepository jpaRepository;
  private final CompanyPersistenceMapper companyPersistenceMapper;

  @Override
  public Company save(Company company) {
    CompanyEntity companyEntity = jpaRepository.save(companyPersistenceMapper.toEntity(company));
    return companyPersistenceMapper.toDomain(companyEntity);
  }
}

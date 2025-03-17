package com.logistic.company.adaptor.out.persistence.repository;

import com.logistic.common.annotation.PersistenceAdapter;
import com.logistic.company.adaptor.out.persistence.CompanyEntity;
import com.logistic.company.adaptor.out.persistence.mapper.CompanyPersistenceMapper;
import com.logistic.company.application.port.in.query.CompanySearchQuery;
import com.logistic.company.application.port.out.CompanyPersistencePort;
import com.logistic.company.domain.Company;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@PersistenceAdapter
@RequiredArgsConstructor
public class CompanyPersistenceAdaptor implements CompanyPersistencePort {
  private final CompanyJpaRepository jpaRepository;
  private final CompanyQueryDslRepository queryDslRepository;
  private final CompanyPersistenceMapper companyPersistenceMapper;

  @Override
  public Company save(final Company company) {
    final CompanyEntity companyEntity = jpaRepository.save(companyPersistenceMapper.toEntity(company));
    return companyPersistenceMapper.toDomain(companyEntity);
  }

  @Override
  public Optional<Company> findById(final Long companyId) {
    return jpaRepository.findById(companyId).map(companyPersistenceMapper::toDomain);
  }

  @Override
  public Page<Company> saerch(final CompanySearchQuery query) {
    return queryDslRepository.search(query).map(companyPersistenceMapper::toDomain);

  }
}

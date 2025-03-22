package com.logistic.company.adapter.out.persistence;

import com.logistic.common.annotation.Adapter;
import com.logistic.company.adapter.out.persistence.mapper.CompanyPersistenceMapper;
import com.logistic.company.adapter.out.persistence.model.CompanyEntity;
import com.logistic.company.adapter.out.persistence.repository.CompanyJpaRepository;
import com.logistic.company.application.port.out.CompanyCommandPersistencePort;
import com.logistic.company.domain.exception.CustomNotFoundException.CompanyNotFoundException;
import com.logistic.company.domain.model.Company;
import lombok.RequiredArgsConstructor;

@Adapter
@RequiredArgsConstructor
public class CompanyCommandPersistenceAdapter implements CompanyCommandPersistencePort {
  private final CompanyJpaRepository jpaRepository;
  private final CompanyPersistenceMapper mapper;

  @Override
  public Company save(final Company company) {
    final CompanyEntity companyEntity = jpaRepository.save(mapper.toEntity(company));
    return mapper.toDomain(companyEntity);
  }

  @Override
  public Company findById(final Long companyId) {
    final CompanyEntity entity = jpaRepository.findById(companyId).orElseThrow(
        CompanyNotFoundException::new);
    return mapper.toDomain(entity);
  }
}

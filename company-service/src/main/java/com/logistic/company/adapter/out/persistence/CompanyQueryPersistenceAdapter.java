package com.logistic.company.adapter.out.persistence;

import com.logistic.common.annotation.Adapter;
import com.logistic.company.adapter.out.persistence.mapper.CompanyPersistenceMapper;
import com.logistic.company.adapter.out.persistence.model.CompanyViewEntity;
import com.logistic.company.adapter.out.persistence.repository.CompanyViewJpaRepository;
import com.logistic.company.adapter.out.persistence.repository.CompanyViewQueryDslRepository;
import com.logistic.company.application.port.in.query.SearchCompanyQuery;
import com.logistic.company.application.port.out.CompanyQueryPersistencePort;
import com.logistic.company.domain.exception.CustomNotFoundException.CompanyNotFoundException;
import com.logistic.company.domain.model.CompanyView;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;

@Adapter
@RequiredArgsConstructor
public class CompanyQueryPersistenceAdapter implements CompanyQueryPersistencePort {

  private final CompanyViewJpaRepository jpaRepository;
  private final CompanyViewQueryDslRepository queryDslRepository;
  private final CompanyPersistenceMapper mapper;

  @Override
  public CompanyView save(final CompanyView companyView) {
    return mapper.toDomain(jpaRepository.save(mapper.toEntity(companyView)));
  }

  @Override
  public CompanyView findById(final Long companyId) {
    final CompanyViewEntity companyViewEntity = jpaRepository.findById(companyId)
        .orElseThrow(CompanyNotFoundException::new);
    return mapper.toDomain(companyViewEntity);
  }

  @Override
  public List<CompanyView> findAll(final List<Long> companyIds) {
    return jpaRepository.findAllById(companyIds).stream().map(mapper::toDomain).toList();
  }

  @Override
  public Page<CompanyView> search(final SearchCompanyQuery query) {
    return queryDslRepository.search(query).map(mapper::toDomain);
  }
}

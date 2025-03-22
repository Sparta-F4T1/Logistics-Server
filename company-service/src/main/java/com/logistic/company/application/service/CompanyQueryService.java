package com.logistic.company.application.service;

import com.logistic.company.application.port.in.CompanyQueryUseCase;
import com.logistic.company.application.port.in.query.FindCompanyQuery;
import com.logistic.company.application.port.in.query.ListCompanyQuery;
import com.logistic.company.application.port.in.query.SearchCompanyQuery;
import com.logistic.company.application.port.out.CompanyQueryPersistencePort;
import com.logistic.company.domain.model.CompanyView;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CompanyQueryService implements CompanyQueryUseCase {
  private final CompanyQueryPersistencePort queryPersistencePort;

  @Override
  public CompanyView findCompany(final FindCompanyQuery query) {
    return queryPersistencePort.findById(query.companyId());
  }

  @Override
  public List<CompanyView> findCompanyList(final ListCompanyQuery query) {
    return queryPersistencePort.findAll(query.companyIds());
  }

  @Override
  public Page<CompanyView> search(final SearchCompanyQuery query) {
    return queryPersistencePort.search(query);
  }
}

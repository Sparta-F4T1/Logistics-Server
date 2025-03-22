package com.logistic.company.application.port.out;

import com.logistic.company.application.port.in.query.SearchCompanyQuery;
import com.logistic.company.domain.model.CompanyView;
import java.util.List;
import org.springframework.data.domain.Page;

public interface CompanyQueryPersistencePort {
  CompanyView save(CompanyView companyView);

  CompanyView findById(Long companyId);

  List<CompanyView> findAll(List<Long> companyIds);

  Page<CompanyView> search(SearchCompanyQuery query);
}

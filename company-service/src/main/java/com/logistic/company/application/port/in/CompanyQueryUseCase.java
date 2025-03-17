package com.logistic.company.application.port.in;

import com.logistic.company.application.port.in.query.CompanyFindQuery;
import com.logistic.company.application.port.in.query.CompanySearchQuery;
import com.logistic.company.domain.Company;
import org.springframework.data.domain.Page;

public interface CompanyQueryUseCase {
  Company findCompany(CompanyFindQuery query);

  Page<Company> search(CompanySearchQuery query);
}

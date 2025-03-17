package com.logistic.company.application.port.out;

import com.logistic.company.application.port.in.query.CompanySearchQuery;
import com.logistic.company.domain.Company;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface CompanyPersistencePort {
  Company save(Company company);

  Optional<Company> findById(Long companyId);

  Page<Company> saerch(CompanySearchQuery query);
}
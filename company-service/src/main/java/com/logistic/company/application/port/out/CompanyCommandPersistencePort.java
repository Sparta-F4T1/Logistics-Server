package com.logistic.company.application.port.out;

import com.logistic.company.domain.model.Company;

public interface CompanyCommandPersistencePort {
  Company save(Company company);

  Company findById(Long companyId);


}
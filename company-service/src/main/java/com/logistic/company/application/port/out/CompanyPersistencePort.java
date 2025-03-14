package com.logistic.company.application.port.out;

import com.logistic.company.domain.Company;

public interface CompanyPersistencePort {
  Company save(Company company);
}
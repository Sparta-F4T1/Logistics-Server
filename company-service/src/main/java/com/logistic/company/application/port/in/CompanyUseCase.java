package com.logistic.company.application.port.in;

import com.logistic.company.application.port.in.command.CompanyCreateCommand;
import com.logistic.company.application.port.in.command.CompanyDeleteCommand;
import com.logistic.company.application.port.in.command.CompanyUpdateCommand;
import com.logistic.company.domain.Company;

public interface CompanyUseCase {
  Company createCompany(CompanyCreateCommand command);

  Company updateCompany(CompanyUpdateCommand command);

  void deleteCompany(CompanyDeleteCommand command);
}
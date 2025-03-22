package com.logistic.company.application.port.in;

import com.logistic.company.application.port.in.command.CreateCompanyCommand;
import com.logistic.company.application.port.in.command.DeleteCompanyCommand;
import com.logistic.company.application.port.in.command.UpdateCompanyCommand;
import com.logistic.company.domain.model.Company;

public interface CompanyCommandUseCase {
  Company createCompany(CreateCompanyCommand command);

  Company updateCompany(UpdateCompanyCommand command);

  void deleteCompany(DeleteCompanyCommand command);
}
package com.logistic.company.application.service;

import com.logistic.common.annotation.UseCase;
import com.logistic.company.application.port.in.CompanyCommandUseCase;
import com.logistic.company.application.port.in.command.CreateCompanyCommand;
import com.logistic.company.application.port.in.command.DeleteCompanyCommand;
import com.logistic.company.application.port.in.command.UpdateCompanyCommand;
import com.logistic.company.application.port.out.CompanyCommandPersistencePort;
import com.logistic.company.application.port.out.CompanyInternalPort;
import com.logistic.company.domain.CompanyPolicyService;
import com.logistic.company.domain.command.CompanyForCreate;
import com.logistic.company.domain.command.CompanyForUpdate;
import com.logistic.company.domain.model.Company;
import com.logistic.company.domain.model.vo.Gps;
import com.logistic.company.domain.model.vo.Hub;
import com.logistic.company.domain.model.vo.User;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@UseCase
@Transactional
@RequiredArgsConstructor
public class CompanyCommandService implements CompanyCommandUseCase {
  private final CompanyInternalPort internalPort;
  private final CompanyCommandPersistencePort persistencePort;
  private final CompanyPolicyService policyService;

  @Override
  public Company createCompany(final CreateCompanyCommand command) {
    final Hub hub = findHub(command.hubId());
    final Gps gps = findGps(command.road());
    final List<User> userList = findUserList(command.userIds());
    policyService.validateCreateCompany(command.passport(), hub);
    final CompanyForCreate forCreate = command.toForCreate(hub, gps, userList);
    final Company company = Company.create(forCreate);
    return persistencePort.save(company);
  }

  @Override
  public Company updateCompany(final UpdateCompanyCommand command) {
    Company company = findCompany(command.companyId());
    final Hub hub = findHub(command.hubId());
    final Gps gps = findGps(command.road());
    final List<User> userList = findUserList(command.userIds());
    policyService.validateUpdateCompany(command.passport(), hub, company);
    final CompanyForUpdate forUpdate = command.toForUpdate(hub, gps, userList);
    company.updateCompany(forUpdate);
    return persistencePort.save(company);
  }

  @Override
  public void deleteCompany(final DeleteCompanyCommand command) {
    Company company = findCompany(command.companyId());
    final Hub hub = findHub(company.getHubId());
    policyService.validateDeleteCompany(command.passport(), hub);
    company.delete();
    persistencePort.save(company);
  }

  private Company findCompany(final Long companyId) {
    return persistencePort.findById(companyId);
  }

  private Hub findHub(final Long hubId) {
    return internalPort.findHub(hubId);
  }

  private Gps findGps(final String road) {
    return internalPort.findGps(road);
  }

  private List<User> findUserList(final List<String> userIds) {
    return internalPort.findUserList(userIds);
  }
}
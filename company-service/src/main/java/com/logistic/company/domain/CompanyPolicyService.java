package com.logistic.company.domain;

import com.logistic.common.annotation.UseCase;
import com.logistic.common.passport.model.Passport;
import com.logistic.common.passport.model.RoleType;
import com.logistic.company.domain.exception.CustomForbiddenException.CreateCompanyForbiddenException;
import com.logistic.company.domain.exception.CustomForbiddenException.DeleteCompanyForbiddenException;
import com.logistic.company.domain.exception.CustomForbiddenException.UpdateCompanyForbiddenException;
import com.logistic.company.domain.model.Company;
import com.logistic.company.domain.model.vo.Hub;
import lombok.NoArgsConstructor;

@UseCase
@NoArgsConstructor
public class CompanyPolicyService {

  public void validateCreateCompany(final Passport passport, final Hub hub) {
    final String userId = passport.getUserInfo().getUserId();
    final String role = passport.getUserInfo().getRole();
    final RoleType roleType = RoleType.valueOf(role);

    switch (roleType) {
      case MASTER_ADMIN:
        return;

      case HUB_ADMIN:
        if (hub != null && hub.userIds().contains(userId)) {
          return;
        }
        throw new CreateCompanyForbiddenException();

      default:
        throw new CreateCompanyForbiddenException();
    }
  }

  public void validateUpdateCompany(final Passport passport, final Hub hub,
                                    final Company company) {
    final String userId = passport.getUserInfo().getUserId();
    final String role = passport.getUserInfo().getRole();
    final RoleType roleType = RoleType.valueOf(role);

    switch (roleType) {
      case MASTER_ADMIN:
        return;

      case HUB_ADMIN:
        if (hub != null && hub.userIds().contains(userId)) {
          return;
        }
        throw new UpdateCompanyForbiddenException();

      case COMPANY_PERSONNEL:
        if (company != null && company.getManagerIds().contains(userId)) {
          return;
        }
        throw new UpdateCompanyForbiddenException();

      default:
        throw new UpdateCompanyForbiddenException();
    }
  }

  public void validateDeleteCompany(final Passport passport, final Hub hub) {
    final String userId = passport.getUserInfo().getUserId();
    final String role = passport.getUserInfo().getRole();
    final RoleType roleType = RoleType.valueOf(role);

    switch (roleType) {

      case MASTER_ADMIN:
        return;

      case HUB_ADMIN:
        if (hub != null && hub.userIds().contains(userId)) {
          return;
        }
        throw new DeleteCompanyForbiddenException();

      default:
        throw new DeleteCompanyForbiddenException();
    }
  }
}

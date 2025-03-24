package com.logistic.product.application.service;

import com.logistic.common.passport.model.Passport;
import com.logistic.common.passport.model.RoleType;
import com.logistic.product.domain.exception.CustomForbiddenException.AccessProductForbiddenException;
import com.logistic.product.domain.exception.CustomForbiddenException.DeleteProductForbiddenException;
import com.logistic.product.domain.exception.CustomForbiddenException.UpdateProductForbiddenException;
import com.logistic.product.domain.vo.Company;
import com.logistic.product.domain.vo.Hub;
import org.springframework.stereotype.Service;

@Service
public class ProductPolicyService {
  public void validateCreateProduct(final Passport passport, final Hub hub, final Company company) {
    final String userId = getUserId(passport);
    final RoleType roleType = getRoleType(passport);

    switch (roleType) {
      case MASTER_ADMIN:
        return;

      case HUB_ADMIN:
        if (checkHubAdmin(userId, hub)) {
          return;
        }
        throw new UpdateProductForbiddenException();
      case COMPANY_PERSONNEL:
        if (checkCompanyPersonnel(userId, company)) {
          return;
        }
        throw new UpdateProductForbiddenException();
      default:
        throw new UpdateProductForbiddenException();
    }
  }

  public void validateUpdateProduct(final Passport passport, final Hub hub, final Company company) {
    final String userId = getUserId(passport);
    final RoleType roleType = getRoleType(passport);

    switch (roleType) {
      case MASTER_ADMIN:
        return;

      case HUB_ADMIN:
        if (checkHubAdmin(userId, hub)) {
          return;
        }
        throw new UpdateProductForbiddenException();
      case COMPANY_PERSONNEL:
        if (checkCompanyPersonnel(userId, company)) {
          return;
        }
        throw new UpdateProductForbiddenException();
      default:
        throw new UpdateProductForbiddenException();
    }
  }

  public void validateDeleteProduct(final Passport passport, final Hub hub) {
    final String userId = getUserId(passport);
    final RoleType roleType = getRoleType(passport);

    switch (roleType) {
      case MASTER_ADMIN:
        return;

      case HUB_ADMIN:
        if (checkHubAdmin(userId, hub)) {
          return;
        }
        throw new DeleteProductForbiddenException();
      default:
        throw new DeleteProductForbiddenException();
    }
  }

  public void validateAccessProduct(final Passport passport, final Hub hub) {
    final String userId = getUserId(passport);
    final RoleType roleType = getRoleType(passport);

    switch (roleType) {
      case MASTER_ADMIN:
        return;

      case HUB_ADMIN:
        if (checkHubAdmin(userId, hub)) {
          return;
        }
        throw new AccessProductForbiddenException();
    }
  }

  private String getUserId(final Passport passport) {
    return passport.getUserInfo().getUserId();
  }

  private RoleType getRoleType(final Passport passport) {
    try {
      return RoleType.valueOf(passport.getUserInfo().getRole());
    } catch (Exception e) {
      throw new AccessProductForbiddenException();
    }
  }

  private boolean checkHubAdmin(final String userId, final Hub hub) {
    return hub != null && hub.userIds().contains(userId);
  }

  private boolean checkCompanyPersonnel(final String userId, final Company company) {
    return company != null && company.userIds().contains(userId);
  }
}

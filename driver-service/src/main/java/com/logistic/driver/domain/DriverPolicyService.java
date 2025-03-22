package com.logistic.driver.domain;

import com.logistic.common.annotation.UseCase;
import com.logistic.common.passport.model.Passport;
import com.logistic.common.passport.model.RoleType;
import com.logistic.driver.domain.exception.CustomForbiddenException.AccessDriverForbiddenException;
import com.logistic.driver.domain.exception.CustomForbiddenException.CreateDriverForbiddenException;
import com.logistic.driver.domain.exception.CustomForbiddenException.DeleteDriverForbiddenException;
import com.logistic.driver.domain.exception.CustomForbiddenException.UpdateDriverForbiddenException;
import com.logistic.driver.domain.model.vo.Hub;

@UseCase
public class DriverPolicyService {

  public void validateCreateDriver(final Passport passport, final Hub hub) {
    final String userId = getUserId(passport);
    final RoleType roleType = getRoleType(passport);

    switch (roleType) {
      case MASTER_ADMIN:
        return;

      case HUB_ADMIN:
        if (checkHubAdmin(userId, hub)) {
          return;
        }
        throw new CreateDriverForbiddenException();

      default:
        throw new CreateDriverForbiddenException();
    }
  }

  public void validateUpdateDriver(final Passport passport, final Hub hub) {
    final String userId = getUserId(passport);
    final RoleType roleType = getRoleType(passport);

    switch (roleType) {
      case MASTER_ADMIN:
        return;

      case HUB_ADMIN:
        if (checkHubAdmin(userId, hub)) {
          return;
        }
        throw new UpdateDriverForbiddenException();

      default:
        throw new UpdateDriverForbiddenException();
    }
  }

  public void validateDeleteDriver(final Passport passport, final Hub hub) {
    final String userId = getUserId(passport);
    final RoleType roleType = getRoleType(passport);

    switch (roleType) {
      case MASTER_ADMIN:
        return;

      case HUB_ADMIN:
        if (checkHubAdmin(userId, hub)) {
          return;
        }
        throw new DeleteDriverForbiddenException();

      default:
        throw new DeleteDriverForbiddenException();
    }
  }

  public void validateAccessDriver(final Passport passport, final Hub hub, final String driverId) {
    final String userId = getUserId(passport);
    final RoleType roleType = getRoleType(passport);

    switch (roleType) {
      case MASTER_ADMIN:
        return;

      case HUB_ADMIN:
        if (checkHubAdmin(userId, hub)) {
          return;
        }
        throw new AccessDriverForbiddenException();
      case DELIVERY_PERSONNEL:
        if (checkDeliveryPersonnel(userId, driverId)) {
          return;
        }
        throw new AccessDriverForbiddenException();
      default:
        throw new AccessDriverForbiddenException();
    }
  }

  private String getUserId(final Passport passport) {
    return passport.getUserInfo().getUserId();
  }

  private RoleType getRoleType(final Passport passport) {
    try {
      return RoleType.valueOf(passport.getUserInfo().getRole());
    } catch (Exception e) {
      throw new AccessDriverForbiddenException();
    }
  }

  private boolean checkHubAdmin(final String userId, final Hub hub) {
    return hub != null && hub.userIds().contains(userId);
  }

  private boolean checkDeliveryPersonnel(final String userId, final String driverId) {
    return driverId != null && driverId.equals(userId);
  }
}

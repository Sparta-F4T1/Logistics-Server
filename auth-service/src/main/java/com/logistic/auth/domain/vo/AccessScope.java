package com.logistic.auth.domain.vo;

import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class AccessScope {
  private RoleName role;

  private AccessScope(RoleName role) {
    this.role = role;
  }

  public static AccessScope of(RoleType roleType) {
    return new AccessScope(RoleName.of(roleType));
  }

  public static AccessScope of(String roleName) {
    return new AccessScope(RoleName.of(roleName));
  }

  public RoleType getRoleEnum() {
    return role.toEnum();
  }
}
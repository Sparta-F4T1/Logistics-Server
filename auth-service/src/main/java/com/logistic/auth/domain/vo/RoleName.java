package com.logistic.auth.domain.vo;

import com.logistic.auth.domain.exception.AuthServiceErrorCode;
import com.logistic.auth.domain.exception.AuthServiceException;
import java.util.Objects;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleName {
  private String value;

  private RoleName(String value) {
    this.value = value;
  }

  public static RoleName of(String value) {
    if (value == null || value.trim().isEmpty()) {
      throw AuthServiceException.user(AuthServiceErrorCode.MISSING_ROLE_NAME);
    }
    return new RoleName(value);
  }

  public static RoleName of(RoleType roleType) {
    return of(roleType.name());
  }

  public RoleType toEnum() {
    try {
      return RoleType.valueOf(value);
    } catch (IllegalArgumentException e) {
      throw AuthServiceException.user(AuthServiceErrorCode.INVALID_ROLE, ": " + value);
    }
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    RoleName roleName = (RoleName) o;
    return Objects.equals(value, roleName.value);
  }

  @Override
  public int hashCode() {
    return Objects.hash(value);
  }
}
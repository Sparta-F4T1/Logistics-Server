package com.logistic.auth.domain.vo;

import com.logistic.auth.domain.exception.AuthServiceErrorCode;
import com.logistic.auth.domain.exception.AuthServiceException;
import lombok.AccessLevel;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class RoleId {
  private Long value;

  private RoleId(Long value) {
    this.value = value;
  }

  public static RoleId of(Long value) {
    if (value == null) {
      throw AuthServiceException.user(AuthServiceErrorCode.MISSING_ROLE_ID);
    }
    return new RoleId(value);
  }
}
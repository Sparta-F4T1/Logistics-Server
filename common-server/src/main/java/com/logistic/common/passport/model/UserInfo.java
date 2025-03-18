package com.logistic.common.passport.model;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class UserInfo implements Serializable {
  private String userId;
  private String role;
  private Long targetId;

  public static UserInfo create(String userId, String role, Long targetId) {
    return new UserInfo(userId, role, targetId);
  }

  public String userId() {
    return this.userId;
  }

  public String role() {
    return this.role;
  }

  public Long targetId() {
    return this.targetId;
  }
}
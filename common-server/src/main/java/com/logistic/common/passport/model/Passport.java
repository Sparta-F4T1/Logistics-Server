package com.logistic.common.passport.model;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class Passport implements Serializable {
  private UserInfo userInfo;
  private SessionInfo sessionInfo;

  public static Passport create(UserInfo userInfo, SessionInfo sessionInfo) {
    return new Passport(userInfo, sessionInfo);
  }

  public void updateSessionInfo(SessionInfo sessionInfo) {
    this.sessionInfo = sessionInfo;
  }

  public UserInfo userInfo() {
    return this.userInfo;
  }

  public SessionInfo sessionInfo() {
    return this.sessionInfo;
  }
}
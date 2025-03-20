package com.logistic.common.passport.model;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Passport implements Serializable {
  private static final long serialVersionUID = 1L;
  private UserInfo userInfo;
  private SessionInfo sessionInfo;

  public Passport updateSessionInfo(SessionInfo sessionInfo) {
    return new Passport(userInfo, sessionInfo);
  }
}
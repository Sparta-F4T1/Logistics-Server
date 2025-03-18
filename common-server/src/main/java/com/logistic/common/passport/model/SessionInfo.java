package com.logistic.common.passport.model;

import java.io.Serializable;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class SessionInfo implements Serializable {
  private String sessionId;
  private long issuedAt;
  private long expiresAt;

  public static SessionInfo create(String sessionId, long issuedAt, long expiresAt) {
    return new SessionInfo(sessionId, issuedAt, expiresAt);
  }

  public String sessionId() {
    return this.sessionId;
  }

  public Long issuedAt() {
    return this.issuedAt;
  }

  public Long expiresAt() {
    return this.expiresAt;
  }
}
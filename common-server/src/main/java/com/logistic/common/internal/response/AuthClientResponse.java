package com.logistic.common.internal.response;

import com.logistic.common.passport.model.Passport;
import lombok.Value;

@Value
public class AuthClientResponse {
  private Boolean successful;
  private String userId;
  private Passport passport;
  private String message;

  public boolean getSuccessful() {
    return Boolean.TRUE.equals(this.successful);
  }
}
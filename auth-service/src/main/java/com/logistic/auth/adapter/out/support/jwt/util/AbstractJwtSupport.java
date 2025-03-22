package com.logistic.auth.adapter.out.support.jwt.util;

import com.logistic.auth.adapter.out.support.jwt.config.JwtConfig;
import java.security.Key;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public abstract class AbstractJwtSupport {
  protected final Key key;

  public AbstractJwtSupport(JwtConfig jwtConfig) {
    this.key = jwtConfig.getKey();
  }
}
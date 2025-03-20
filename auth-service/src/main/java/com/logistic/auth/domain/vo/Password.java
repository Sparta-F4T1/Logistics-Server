package com.logistic.auth.domain.vo;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Password {
  private final String hashedValue;

  public static Password mock() {
//      hashedValue
    return new Password("$2a$10$J7u6Tt5qxE5EyF1a6Bv8M.s8nZmX05oOQJmK6UmA5sBJD7ilTeHqi");
  }
}
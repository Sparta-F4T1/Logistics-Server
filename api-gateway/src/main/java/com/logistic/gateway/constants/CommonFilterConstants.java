package com.logistic.gateway.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CommonFilterConstants {
  public static final String TOKEN_ATTR = "token";
  public static final String ERROR_MISSING_TOKEN = "토큰이 존재하지 않습니다.";
}
